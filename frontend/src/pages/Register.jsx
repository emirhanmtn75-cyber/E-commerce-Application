import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/axios";
import "./Login.css";

function Register() {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");
    setLoading(true);

    try {
      await api.post("/auth/register", {
        name,
        email,
        password,
      });

      setSuccess("Kayıt başarılı! Giriş sayfasına yönlendiriliyorsun...");

      setTimeout(() => {
        navigate("/");
      }, 1200);
    } catch (err) {
      const status = err.response?.status;
      const message = err.response?.data?.message;

      if (status === 409 || message === "Email already exists") {
        setError("Bu email zaten kullanılıyor.");
      } else if (message) {
        setError(message);
      } else {
        setError("Kayıt başarısız. Bağlantı veya sunucu hatası oluştu.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="title">E-Commerce</h1>
        <p className="subtitle">Yeni hesap oluştur</p>

        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label>Ad Soyad</label>
            <input
              type="text"
              placeholder="Emirhan Metin"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div className="input-group">
            <label>Email</label>
            <input
              type="email"
              placeholder="ornek@mail.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="input-group">
            <label>Şifre</label>
            <input
              type="password"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          {error && <div className="error">{error}</div>}
          {success && <div className="success">{success}</div>}

          <button className="login-btn" disabled={loading}>
            {loading ? "Kayıt oluşturuluyor..." : "Kayıt Ol"}
          </button>
        </form>

        <p className="footer-text">
          Zaten hesabın var mı? <Link to="/">Giriş Yap</Link>
        </p>
      </div>
    </div>
  );
}

export default Register;
