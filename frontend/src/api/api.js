import axios from "axios";

const API = axios.create({
  baseURL: "/api",
});

export const getOrders = () => API.get("/orders");
export const createOrder = (order) => API.post("/orders", order);
export const deleteOrder = (id) => API.delete(`/orders/${id}`);

export const getPredictions = () => API.get("/predictions/demand");
export const getInsights = () => API.get("/predictions/insights");
export const getPrepTime = (currentOrders) => API.get(`/predictions/prep-time?currentOrders=${currentOrders}`);
export const getSurgePricing = (currentOrders) => API.get(`/predictions/surge-pricing?currentOrders=${currentOrders}`);
export const getExpiringInventory = () => API.get("/inventory/expiring");

export default API;