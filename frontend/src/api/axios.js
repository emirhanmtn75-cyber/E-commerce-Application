import axios from "axios";

const baseURL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

const api = axios.create({
  baseURL: baseURL.replace(/\/$/, ""),
});

// JWT otomatik header’a eklensin, auth endpointlerinde eski token gönderilmesin.
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  const url = config.url || "";
  const isAuthEndpoint = url.startsWith("/auth/");

  if (token && token !== "undefined" && token !== "null" && !isAuthEndpoint) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
