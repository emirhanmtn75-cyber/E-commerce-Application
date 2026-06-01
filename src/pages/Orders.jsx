import { useEffect, useState } from "react";
import api from "../api/axios";
import "./Orders.css";

function Orders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    api.get("/api/orders")
      .then(res => setOrders(res.data))
      .catch(() => console.log("Siparişler alınamadı"));
  }, []);

  return (
    <div className="orders-container">
      <h2>Siparişlerim</h2>

      {orders.length === 0 ? (
        <p>Henüz siparişiniz yok.</p>
      ) : (
        orders.map(order => (
          <div key={order.id} className="order-card">
            <h4>Sipariş ID: {order.id}</h4>
            <p>Tarih: {order.orderDate}</p>
            <p>Toplam: {order.totalPrice} ₺</p>

            <div className="order-items">
              {order.items.map(item => (
                <div key={item.id}>
                  {item.productName} x {item.quantity}
                </div>
              ))}
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export default Orders;
