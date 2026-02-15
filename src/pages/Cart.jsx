import { useEffect, useState } from "react";
import api from "../api/axios";
import "./Cart.css";

function Cart() {
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchCart = async () => {
    try {
      const res = await api.get("/api/cart");
      setCart(res.data);
    } catch (err) {
      console.error("Sepet alınamadı");
    } finally {
      setLoading(false);
    }
  };

  const removeItem = async (id) => {
    try {
      await api.delete(`/api/cart/remove/${id}`);
      fetchCart();
    } catch (err) {
      console.error("Silme hatası");
    }
  };

  const placeOrder = async () => {
    try {
      await api.post("/api/orders");
      alert("Sipariş oluşturuldu");
      fetchCart();
    } catch (err) {
      alert("Sipariş oluşturulamadı");
    }
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const totalPrice =
    cart?.items?.reduce(
      (total, item) => total + item.price * item.quantity,
      0
    ) || 0;

  if (loading) return <p>Yükleniyor...</p>;

  return (
    <div className="cart-container">
      <h2>Sepetim</h2>

      {!cart || cart.items.length === 0 ? (
        <p>Sepetiniz boş</p>
      ) : (
        <>
          <div className="cart-items">
            {cart.items.map((item) => (
              <div key={item.cartItemId} className="cart-item">
                <div>
                  <h4>{item.productName}</h4>
                  <p>{item.price} ₺</p>
                  <p>Adet: {item.quantity}</p>
                </div>
                <button onClick={() => removeItem(item.cartItemId)}>
                  Sil
                </button>
              </div>
            ))}
          </div>

          <div className="cart-summary">
            <h3>Toplam: {totalPrice} ₺</h3>
            <button onClick={placeOrder}>Sipariş Ver</button>
          </div>
        </>
      )}
    </div>
  );
}

export default Cart;