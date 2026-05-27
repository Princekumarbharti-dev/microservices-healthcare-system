import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function RequireAuth({ registeredOnly }: { registeredOnly?: boolean }) {
  const a = useAuth();

  if (!a.isAuthed) return <Navigate to="/login" replace />;

  if (registeredOnly && a.role && a.role.toUpperCase() !== "REGISTERED_USER") {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
}