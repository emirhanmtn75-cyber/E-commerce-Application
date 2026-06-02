import { useState } from "react";
import api from "../api/axios";

function AdminProducts() {
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");

  const createProduct = async () => {
    await api.post("/api/products", {
      name,
      price,
      stock: 10,
      categoryId: 1
    });
    alert("Ürün oluşturuldu");
  };

  return (
    <div>
      <h2>Admin Ürün Oluştur</h2>
      <input placeholder="Ad" onChange={e => setName(e.target.value)} />
      <input placeholder="Fiyat" onChange={e => setPrice(e.target.value)} />
      <button onClick={createProduct}>Kaydet</button>
    </div>
  );
}

export default AdminProducts;
