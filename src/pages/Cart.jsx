import { useEffect, useState } from "react";
import api from "../api/axios";
import "./Cart.css";

function Cart() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [coupon, setCoupon] = useState("");

  const fetchCart = async () => {
    try {
      const res = await api.get("/api/cart");
      setItems(res.data);
    } catch (err) {
      console.error("Sepet alınamadı", err);
    } finally {
      setLoading(false);
    }
  };

  const removeItem = async (cartItemId) => {
    try {
      await api.delete(`/api/cart/remove/${cartItemId}`);
      fetchCart();
    } catch (err) {
      console.error("Silme hatası");
    }
  };

  const updateQuantity = async (cartItemId, newQty) => {
    if (newQty < 1) return;
    try {
      await api.put(`/api/cart/update/${cartItemId}`, {
        quantity: newQty,
      });
      fetchCart();
    } catch (err) {
      console.error("Güncelleme hatası");
    }
  };

  const placeOrder = async () => {
    try {
      await api.post("/api/orders");
      alert("Siparişiniz başarıyla oluşturuldu 🎉");
      setItems([]);
    } catch (err) {
      alert("Sipariş oluşturulamadı.");
    }
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const totalPrice = items.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

  const freeShippingLimit = 500;
  const remainingForFree = freeShippingLimit - totalPrice;

  if (loading) return <div className="loader">Yükleniyor...</div>;

  return (
    <div className="cart-page">
      <div className="cart-container">
        <header className="cart-header">
          <h1>Sepetim ({items.length} Ürün)</h1>
        </header>

        {items.length === 0 ? (
          <div className="empty-cart">
            <div className="empty-icon">🛍️</div>
            <h2>Sepetin boş</h2>
            <p>Alışverişe başlamak için ürünleri keşfet!</p>
            <button className="btn-checkout">Alışverişe Başla</button>
          </div>
        ) : (
          <div className="cart-content">
            <div className="cart-items-list">
              {items.map((item) => (
                <div key={item.cartItemId} className="cart-item-card">
                  <img
                    src={item.imageUrl || "https://via.placeholder.com/100"}
                    alt={item.productName}
                    className="item-image"
                  />

                  <div className="item-details">
                    <h4>{item.productName}</h4>
                    <div className="seller">Satıcı: TrendShop</div>
                    <div className="item-price">
                      {item.price.toLocaleString()} ₺
                    </div>

                    <div className="quantity-control">
                      <button
                        onClick={() =>
                          updateQuantity(
                            item.cartItemId,
                            item.quantity - 1
                          )
                        }
                      >
                        -
                      </button>
                      <span>{item.quantity}</span>
                      <button
                        onClick={() =>
                          updateQuantity(
                            item.cartItemId,
                            item.quantity + 1
                          )
                        }
                      >
                        +
                      </button>
                    </div>
                  </div>

                  <button
                    className="btn-remove"
                    onClick={() => removeItem(item.cartItemId)}
                  >
                    ✕
                  </button>
                </div>
              ))}
            </div>

            <aside className="cart-summary-card">
              {remainingForFree > 0 ? (
                <div className="shipping-bar">
                  {remainingForFree.toLocaleString()} ₺ daha ekle, ücretsiz
                  kargo kazan!
                </div>
              ) : (
                <div className="shipping-bar success">
                  🎉 Ücretsiz kargo kazandın!
                </div>
              )}

              <h3>Sipariş Özeti</h3>

              <div className="summary-row">
                <span>Ara Toplam</span>
                <span>{totalPrice.toLocaleString()} ₺</span>
              </div>

              <div className="summary-row">
                <span>Kargo</span>
                <span className="free">Ücretsiz</span>
              </div>

              <div className="summary-row total">
                <span>Toplam</span>
                <span>{totalPrice.toLocaleString()} ₺</span>
              </div>

              <div className="coupon-box">
                <input
                  type="text"
                  placeholder="Kupon kodu"
                  value={coupon}
                  onChange={(e) => setCoupon(e.target.value)}
                />
                <button>Uygula</button>
              </div>

              <button className="btn-checkout" onClick={placeOrder}>
                Ödemeye Geç
              </button>

              <p className="secure">🔒 256-bit SSL Güvenli Ödeme</p>
            </aside>
          </div>
        )}
      </div>
    </div>
  );
}

export default Cart;
