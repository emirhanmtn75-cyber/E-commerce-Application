import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Products from "./pages/Products";
import Cart from "./pages/Cart";
import ProtectedRoute from "./auth/ProtectedRoute";

function App() {
  return (
    <Routes>
      {/* Public */}
      <Route path="/" element={<Login />} />

      {/* Protected */}
      <Route
        path="/products"
        element={
          <ProtectedRoute>
            <Products />
          </ProtectedRoute>
        }
      />

      <Route
        path="/cart"
        element={
          <ProtectedRoute>
            <Cart />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}

export default App;