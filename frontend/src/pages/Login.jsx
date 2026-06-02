import { useState, useContext } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/axios";
import { AuthContext } from "../auth/AuthContext";
import "./Login.css";

function Login() {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const res = await api.post("/auth/login", {
        email,
        password,
      });

      login(res.data.message);
      navigate("/products");
    } catch (err) {
      setError("Email veya şifre hatalı");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="title">E-Commerce</h1>
        <p className="subtitle">Hesabına giriş yap</p>

        <form onSubmit={handleSubmit}>
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

          <button className="login-btn" disabled={loading}>
            {loading ? "Giriş yapılıyor..." : "Giriş Yap"}
          </button>
        </form>

        <p className="footer-text">
          Hesabın yok mu? <Link to="/register">Kayıt Ol</Link>
        </p>
      </div>
    </div>
  );
}

export default Login;