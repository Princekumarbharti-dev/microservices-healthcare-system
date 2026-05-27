import React, { createContext, useContext, useEffect, useMemo, useState } from "react";
import type { LoginResponseDTO } from "../types/auth";
import { authApi } from "../api/auth.api";

type AuthState = {
  token: string | null;
  userId: number | null;
  email: string | null;
  role: string | null;
};

type AuthCtx = AuthState & {
  isAuthed: boolean;
  login: (r: LoginResponseDTO) => void;
  logout: () => void;
  refreshValidate: () => Promise<void>;
};

const Ctx = createContext<AuthCtx | null>(null);

const readAuth = (): AuthState => ({
  token: localStorage.getItem("token"),
  userId: localStorage.getItem("userId") ? Number(localStorage.getItem("userId")) : null,
  email: localStorage.getItem("email"),
  role: localStorage.getItem("role"),
});

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [s, setS] = useState<AuthState>(() => readAuth());

  const login = (r: LoginResponseDTO) => {
    localStorage.setItem("token", r.token);
    localStorage.setItem("userId", String(r.userId));
    localStorage.setItem("email", r.email);
    localStorage.setItem("role", r.role);
    setS({ token: r.token, userId: r.userId, email: r.email, role: r.role });
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("email");
    localStorage.removeItem("role");
    setS({ token: null, userId: null, email: null, role: null });
  };

  const refreshValidate = async () => {
    if (!localStorage.getItem("token")) return;
    const v = await authApi.validate();
    if (!v.valid) {
      logout();
      return;
    }
    setS((p) => ({
      ...p,
      userId: v.userId ?? p.userId,
      email: v.email ?? p.email,
      role: v.role ?? p.role,
    }));
  };

  useEffect(() => {
    refreshValidate().catch(() => {});
    
  }, []);

  const value = useMemo<AuthCtx>(
    () => ({
      ...s,
      isAuthed: !!s.token,
      login,
      logout,
      refreshValidate,
    }),
    [s]
  );

  return <Ctx.Provider value={value}>{children}</Ctx.Provider>;
}

export function useAuth() {
  const v = useContext(Ctx);
  if (!v) throw new Error("useAuth must be used within AuthProvider");
  return v;
}