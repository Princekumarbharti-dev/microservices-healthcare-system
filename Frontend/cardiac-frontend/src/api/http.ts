import axios from "axios";

const baseURL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8087";

export const http = axios.create({
  baseURL,
  headers: { "Content-Type": "application/json" },
});

http.interceptors.request.use((cfg) => {
  const t = localStorage.getItem("token");
  if (t) cfg.headers.Authorization = `Bearer ${t}`;
  return cfg;
});