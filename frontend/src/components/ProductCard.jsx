import { useState } from "react";
import api from "../api/axios";
import "./components/ProductCard.css";

function ProductCard({ product }) {
  const [showToast, setShowToast] = useState(false);

  const addToCart = async () => {
    try {
      await api.post("/api/cart/add", {
        productId: product.id,
        quantity: 1
      });

      setShowToast(true);

      setTimeout(() => {
        setShowToast(false);
      }, 2500);

    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <div className="product-card">
        <h3>{product.name}</h3>
        <p className="price">{product.price} ₺</p>
        <button className="add-btn" onClick={addToCart}>
          🛒 Sepete Ekle
        </button>
      </div>

      {showToast && (
        <div className="toast">
          ✅ Ürün sepete eklendi
        </div>
      )}
    </>
  );
}

export default ProductCard;