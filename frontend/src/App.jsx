import React, { useEffect, useState } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts';
import API from "./api/api";
import logo from "./images/KitchenIQ.png";

// Inline SVG Icons for fast, dependency-free loading
const Icons = {
  Brain: () => <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M12 5a3 3 0 1 0-5.997.125 4 4 0 0 0-2.526 5.77 4 4 0 0 0 .556 6.588A4 4 0 1 0 12 18Z"/><path d="M12 5a3 3 0 1 1 5.997.125 4 4 0 0 1 2.526 5.77 4 4 0 0 1-.556 6.588A4 4 0 1 1 12 18Z"/><path d="M15 13a4.5 4.5 0 0 1-3-4 4.5 4.5 0 0 1-3 4"/><path d="M17.599 6.5a3 3 0 0 0 .399-1.375"/></svg>,
  TrendingUp: () => <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><polyline points="22 7 13.5 15.5 8.5 10.5 2 17"/><polyline points="16 7 22 7 22 13"/></svg>,
  Clock: () => <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>,
  Alert: () => <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>,
  ChefHat: () => <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M17 21v-3.53a2 2 0 0 0-2-2h-6a2 2 0 0 0-2 2V21"/><path d="M4 14.5a3 3 0 1 1 5-2.5 3 3 0 1 1 6 0 3 3 0 1 1 5 2.5 2.5 2.5 0 0 1-2.5 2.5h-11A2.5 2.5 0 0 1 4 14.5Z"/></svg>
};

function App() {
  const [orders, setOrders] = useState([]);
  const [predictions, setPredictions] = useState([]);
  const [insights, setInsights] = useState({});
  const [expiringItems, setExpiringItems] = useState([]);
  const [prepTimeInfo, setPrepTimeInfo] = useState(null);
  const [surgeMultiplier, setSurgeMultiplier] = useState(1.0);
  const [formData, setFormData] = useState({ customerName: "", foodItem: "", quantity: 1 });
  const [errorMsg, setErrorMsg] = useState("");

  const fetchData = async () => {
    try {
      const ordersRes = await API.get("/orders");
      setOrders(ordersRes.data || []);

      const predictRes = await API.get("/predictions/demand");
      setPredictions(predictRes.data || []);

      const insightsRes = await API.get("/predictions/insights");
      setInsights(insightsRes.data || {});

      const expiringRes = await API.get("/inventory/expiring");
      setExpiringItems(expiringRes.data || []);

      const pendingCount = (ordersRes.data || []).filter(o => o.status === 'PENDING').length;
      const prepRes = await API.get(`/predictions/prep-time?currentOrders=${pendingCount}`);
      setPrepTimeInfo(prepRes.data.estimatedPrepTime);

      const surgeRes = await API.get(`/predictions/surge-pricing?currentOrders=${pendingCount}`);
      setSurgeMultiplier(surgeRes.data.surgeMultiplier || 1.0);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
    const interval = setInterval(fetchData, 10000); // Auto-refresh every 10s for "Live" feel
    return () => clearInterval(interval);
  }, []);

  const placeOrder = async (e) => {
    e.preventDefault();
    setErrorMsg("");
    try {
      await API.post("/orders", formData);
      setFormData({ customerName: "", foodItem: "", quantity: 1 });
      fetchData();
    } catch (error) {
      setErrorMsg(error.response?.data?.message?.split(":").pop() || "Failed to place order.");
    }
  };

  const markCompleted = async (id) => {
    try {
      await API.put(`/orders/${id}/status?status=COMPLETED`);
      fetchData();
    } catch (error) {
      console.error("Error updating order status:", error);
    }
  };

  const pendingOrders = orders.filter(o => o.status === "PENDING");
  const completedOrders = orders.filter(o => o.status === "COMPLETED");

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900 font-sans flex flex-col">
      {/* Top Navigation */}
      <nav className="bg-white border-b border-slate-200 px-6 py-4 flex items-center justify-between sticky top-0 z-10 shadow-sm">
        <div className="flex items-center gap-4">
          <div className="flex-shrink-0 bg-white p-1 rounded-xl shadow-sm border border-slate-100">
            <img src={logo} alt="KitchenIQ Logo" className="h-12 w-auto object-contain drop-shadow-sm" />
          </div>
          <div className="flex flex-col">
            <h1 className="text-2xl font-black tracking-tight text-slate-900 leading-none">
              Kitchen<span className="text-blue-600">IQ</span>
            </h1>
            <span className="text-[10px] font-extrabold text-blue-500/80 uppercase tracking-[0.25em] mt-1">Enterprise Intelligence</span>
          </div>
        </div>
        <div className="flex items-center gap-4">
          {surgeMultiplier > 1.0 && (
            <div className="flex items-center gap-2 px-4 py-2 rounded-full font-medium shadow-inner bg-orange-100 text-orange-800 border border-orange-300 animate-pulse">
              <span>🔥 Surge Pricing: {(surgeMultiplier * 100 - 100).toFixed(0)}%</span>
            </div>
          )}
          <div className={`flex items-center gap-2 px-4 py-2 rounded-full font-medium shadow-inner ${prepTimeInfo > 30 ? 'bg-red-50 text-red-700 border border-red-200' : 'bg-green-50 text-green-700 border border-green-200'}`}>
            <Icons.Clock />
            <span>Live Prep: {prepTimeInfo || '--'} mins</span>
            {prepTimeInfo > 30 && <span className="flex h-3 w-3 relative ml-1"><span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-red-400 opacity-75"></span><span className="relative inline-flex rounded-full h-3 w-3 bg-red-500"></span></span>}
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main className="flex-1 max-w-7xl w-full mx-auto p-6 grid grid-cols-1 lg:grid-cols-12 gap-6">
        
        {/* Left Column: Operations (8 cols) */}
        <div className="lg:col-span-8 flex flex-col gap-6">
          
          {/* AI Insights Banner */}
          <div className="bg-gradient-to-r from-indigo-900 to-blue-900 rounded-2xl p-6 text-white shadow-xl relative overflow-hidden">
            <div className="absolute top-0 right-0 opacity-10 transform translate-x-1/4 -translate-y-1/4">
              <Icons.Brain size={120} />
            </div>
            <div className="relative z-10 flex items-start gap-4">
              <div className="p-3 bg-white/20 rounded-xl backdrop-blur-sm">
                <Icons.Brain />
              </div>
              <div>
                <h2 className="text-xl font-bold mb-2">System Insights</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {Object.entries(insights).map(([key, val], idx) => (
                    <div key={idx} className="bg-white/10 rounded-lg p-3 backdrop-blur-sm border border-white/20">
                      <p className="text-blue-200 text-xs uppercase font-bold tracking-wider mb-1">{key}</p>
                      <p className="text-sm font-medium">{val}</p>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>

          {/* AI Demand Chart */}
          <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-lg font-bold flex items-center gap-2">
                <Icons.TrendingUp /> Today's Demand Forecast
              </h2>
            </div>
            <div className="h-64 w-full">
              {predictions.length > 0 ? (
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={predictions} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
                    <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#E2E8F0" />
                    <XAxis dataKey="itemName" axisLine={false} tickLine={false} tick={{fill: '#64748B', fontSize: 12}} />
                    <YAxis axisLine={false} tickLine={false} tick={{fill: '#64748B', fontSize: 12}} />
                    <Tooltip cursor={{fill: '#F1F5F9'}} contentStyle={{borderRadius: '8px', border: 'none', boxShadow: '0 4px 6px -1px rgb(0 0 0 / 0.1)'}} />
                    <Bar dataKey="predictedQuantity" fill="#4F46E5" radius={[4, 4, 0, 0]} barSize={40} />
                  </BarChart>
                </ResponsiveContainer>
              ) : (
                <div className="h-full flex items-center justify-center text-slate-400">Not enough data for predictions</div>
              )}
            </div>
          </div>

          {/* Active Orders Kanban style */}
          <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200 flex-1">
            <h2 className="text-lg font-bold mb-4 flex items-center gap-2 border-b border-slate-100 pb-4">
               Active Kitchen Queue <span className="bg-blue-100 text-blue-700 px-2 py-0.5 rounded-full text-sm">{pendingOrders.length}</span>
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {pendingOrders.map((order) => (
                <div key={order.id} className="group relative bg-slate-50 border border-slate-200 p-4 rounded-xl hover:shadow-md transition-shadow hover:border-blue-300">
                  <div className="flex justify-between items-start mb-2">
                    <div>
                      <p className="font-bold text-slate-800">#{order.id.toString().padStart(4, '0')} - {order.customerName}</p>
                      <p className="text-slate-600 font-medium text-lg">{order.quantity}x {order.foodItem?.name || "Unknown Item"}</p>
                    </div>
                  </div>
                  <div className="mt-4 pt-3 border-t border-slate-200 flex justify-end">
                    <button 
                      onClick={() => markCompleted(order.id)}
                      className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors shadow-sm"
                    >
                      Mark Ready
                    </button>
                  </div>
                </div>
              ))}
              {pendingOrders.length === 0 && (
                <div className="col-span-full py-8 text-center text-slate-400 border-2 border-dashed border-slate-200 rounded-xl">
                  Queue is clear. Kitchen is ready.
                </div>
              )}
            </div>
          </div>
        </div>

        {/* Right Column: Input & Notifications (4 cols) */}
        <div className="lg:col-span-4 flex flex-col gap-6">
          
          {/* New Order Form */}
          <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
            <h2 className="text-lg font-bold mb-4">Manual POS Entry</h2>
            <form onSubmit={placeOrder} className="flex flex-col gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Customer Name</label>
                <input
                  type="text"
                  name="customerName"
                  value={formData.customerName}
                  onChange={(e) => setFormData({...formData, customerName: e.target.value})}
                  className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
                  placeholder="John Doe"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Food Item Name</label>
                <input
                  type="text"
                  name="foodItem"
                  value={formData.foodItem}
                  onChange={(e) => setFormData({...formData, foodItem: e.target.value})}
                  className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
                  placeholder="e.g. Burger"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Quantity</label>
                <input
                  type="number"
                  name="quantity"
                  value={formData.quantity}
                  onChange={(e) => setFormData({...formData, quantity: e.target.value})}
                  className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
                  min="1"
                  required
                />
              </div>
              {errorMsg && <p className="text-red-500 text-sm font-medium bg-red-50 p-2 rounded">{errorMsg}</p>}
              <button 
                type="submit" 
                className={`w-full text-white font-bold py-4 rounded-xl mt-2 transition-all duration-200 transform hover:scale-[1.02] active:scale-[0.98] shadow-lg hover:shadow-xl cursor-pointer flex items-center justify-center gap-2 ${
                  surgeMultiplier > 1.0 
                  ? 'bg-gradient-to-r from-orange-500 to-red-600 hover:from-orange-600 hover:to-red-700 shadow-orange-200' 
                  : 'bg-gradient-to-r from-slate-800 to-slate-900 hover:from-slate-900 hover:to-black shadow-slate-200'
                }`}
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"><path d="m5 11 4-7"/><path d="m19 11-4-7"/><path d="M2 11h20"/><path d="m3.5 11 1.6 7.4a2 2 0 0 0 2 1.6h9.8a2 2 0 0 0 2-1.6l1.7-7.4"/><path d="m9 11 1 9"/><path d="M4.5 15.5h15"/><path d="m15 11-1 9"/></svg>
                {surgeMultiplier > 1.0 ? `Rush Order (+${(surgeMultiplier * 100 - 100).toFixed(0)}%)` : 'Send to Kitchen'}
              </button>
            </form>
          </div>

          {/* Smart Alerts Module (Prepared for Backend integration) */}
          <div className="bg-amber-50 rounded-2xl p-6 shadow-sm border border-amber-200">
            <h2 className="text-lg font-bold mb-4 text-amber-800 flex items-center gap-2">
              <Icons.Alert /> Smart Alerts
            </h2>
            <div className="space-y-3">
              <div className="bg-white p-3 rounded-lg border border-amber-100 shadow-sm flex gap-3 items-start">
                <div className="w-2 h-2 mt-2 rounded-full bg-amber-500 flex-shrink-0"></div>
                <div>
                  <p className="text-sm font-semibold text-slate-800">High Demand Expected</p>
                  <p className="text-xs text-slate-600 mt-1">AI predicts 20% spike in orders between 19:00 - 20:00. Pre-prep advised.</p>
                </div>
              </div>
              
              {expiringItems.length > 0 ? (
                expiringItems.map(item => (
                  <div key={item.id} className="bg-white p-3 rounded-lg border border-red-200 shadow-sm flex gap-3 items-start">
                    <div className="w-2 h-2 mt-2 rounded-full bg-red-500 flex-shrink-0 animate-pulse"></div>
                    <div>
                      <p className="text-sm font-semibold text-red-700">Waste Warning: {item.ingredient.name}</p>
                      <p className="text-xs text-slate-600 mt-1">
                        {item.currentStock} {item.ingredient.unit} expiring on {new Date(item.expirationDate).toLocaleDateString()}. Consider putting this ingredient on special!
                      </p>
                    </div>
                  </div>
                ))
              ) : (
                <div className="bg-white p-3 rounded-lg border border-green-100 shadow-sm flex gap-3 items-start">
                  <div className="w-2 h-2 mt-2 rounded-full bg-green-500 flex-shrink-0"></div>
                  <div>
                    <p className="text-sm font-semibold text-slate-800">Inventory Healthy</p>
                    <p className="text-xs text-slate-600 mt-1">No ingredients are at risk of expiring within the next 3 days. Excellent waste management!</p>
                  </div>
                </div>
              )}
            </div>
          </div>

        </div>
      </main>
    </div>
  );
}

export default App;