import { useEffect, useState } from "react";
import api from "../api/axios";
import "./Products.css";

function Products() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetchProducts();
  }, []);
const addToCart = async (productId) => {
  try {
    await api.post("/api/cart/add", {
      productId: productId,
      quantity: 1,
    });

    alert("Ürün sepete eklendi");
  } catch (err) {
    alert("Sepete eklenemedi");
  }
};

  const fetchProducts = async () => {
    try {
      const res = await api.get("/api/products");
      setProducts(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="marketplace">

      {/* TOP BAR */}
      <div className="topbar">
        <span>İndirim Kuponlarım</span>
        <span>Hakkımızda</span>
        <span>Yardım & Destek</span>
      </div>

      {/* MAIN HEADER */}
      <div className="header">
        <div className="logo">ecommerce</div>

        <input
          className="search"
          placeholder="Ürün, kategori veya marka ara"
        />

        <div className="header-actions">
          <span>Hesabım</span>
          <span>Favorilerim</span>
          <span className="cart">Sepetim</span>
        </div>
      </div>

      {/* CATEGORY BAR */}
      <div className="category-bar">
        <span>Kadın</span>
        <span>Erkek</span>
        <span>Anne & Çocuk</span>
        <span>Ev & Yaşam</span>
        <span>Elektronik</span>
        <span>Kozmetik</span>
        <span>Spor</span>
      </div>

      {/* ICON SLIDER */}
      <div className="icon-row">
        <div className="icon-item">🔥 Fırsatlar</div>
        <div className="icon-item">💄 Kozmetik</div>
        <div className="icon-item">👟 Ayakkabı</div>
        <div className="icon-item">📱 Elektronik</div>
        <div className="icon-item">🛋 Ev</div>
        <div className="icon-item">🎮 Gaming</div>
      </div>

      {/* PRODUCT GRID */}
      <div className="product-section">
        <h2>Sana Özel Ürünler</h2>

        <div className="product-grid">
          {products.map((product) => (
            <div key={product.id} className="product-card">

  <div className="image-wrapper">
    <img
      src={product.imageUrl || "https://via.placeholder.com/300"}
      alt={product.name}
    />
    <span className="favorite">♡</span>
    <span className="discount">%20</span>
  </div>

  <div className="product-info">
    <p className="brand">MARKA</p>
    <p className="product-title">{product.name}</p>
    <span className="price">{product.price} ₺</span>
  </div>

  <button onClick={() => addToCart(product.id)}>
  Sepete Ekle
</button>

</div>
          ))}
        </div>
      </div>

    </div>
  );
}

export default Products;