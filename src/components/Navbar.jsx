import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../auth/AuthContext";
import "./Navbar.css";

function Navbar() {
  const navigate = useNavigate();
  const { logout } = useContext(AuthContext);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="navbar">
      <div className="logo" onClick={() => navigate("/products")}>
        E-Commerce
      </div>

      <div className="nav-links">
        <button onClick={() => navigate("/products")}>
          Ürünler
        </button>

        <button onClick={() => navigate("/cart")}>
          Sepetim
        </button>

        <button className="logout-btn" onClick={handleLogout}>
          Çıkış Yap
        </button>
      </div>
    </div>
  );
}

export default Navbar;