import { http } from "./http";
import type {
  LoginRequestDTO,
  LoginResponseDTO,
  ValidateResponseDTO,
  UpdatePasswordRequestDTO,
  ForgotPasswordRequestDTO
} from "../types/auth";

export const authApi = {

  login: async (b: LoginRequestDTO) => {
    const r = await http.post<LoginResponseDTO>("/auth/login", b);
    return r.data;
  },

  validate: async () => {
    const r = await http.get<ValidateResponseDTO>("/auth/validate");
    return r.data;
  },

  changePassword: async (b: UpdatePasswordRequestDTO) => {
    const r = await http.put<{ message: string }>("/auth/password", b);
    return r.data;
  },

  forgotPassword: async (b: ForgotPasswordRequestDTO) => {
    const r = await http.put<{ message: string }>("/auth/forgot-password", b);
    return r.data;
  }

};