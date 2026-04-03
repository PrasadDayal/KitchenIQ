import { useState } from "react";
import { createOrder } from "../api/api";

function OrderForm({ refresh }) {
    const [name, setName] = useState("");
    const [item, setItem] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        await createOrder({ name, item });
        setName("");
        setItem("");
        refresh();
    };

    return (
        <form className="form" onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Customer Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
            <input
                type="text"
                placeholder="Food Item"
                value={item}
                onChange={(e) => setItem(e.target.value)}
                required
            />
            <button type="submit">Add Order</button>
        </form>
    );
}

export default OrderForm;