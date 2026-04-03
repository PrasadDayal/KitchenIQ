import API from "../api";

useEffect(() => {
  API.get("/orders")
    .then(res => setOrders(res.data));
}, []);