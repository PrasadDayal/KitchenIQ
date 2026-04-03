import { useEffect, useState } from "react";
import { getOrders } from "../api/api";
import OrderForm from "../components/OrderForm";
import OrderList from "../components/OrderList";

function Dashboard() {
    const [orders, setOrders] = useState([]);

    const fetchOrders = async () => {
        const res = await getOrders();
        setOrders(res.data);
    };

    useEffect(() => {
        fetchOrders();
    }, []);

    return (
        <div className="container">
            <OrderForm refresh={fetchOrders} />
            <OrderList orders={orders} refresh={fetchOrders} />
        </div>
    );
}

export default Dashboard;