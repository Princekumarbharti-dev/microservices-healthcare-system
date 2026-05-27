import { http } from "./http";
import type {
  UserProfileRequestDTO,
  UserProfileResponseDTO,
  UserProfileUpdateDTO,
} from "../types/userprofile";

export const userProfileApi = {
  register: async (b: UserProfileRequestDTO) => {
    const r = await http.post<UserProfileResponseDTO>("/userprofile/register", b);
    return r.data;
  },
  getById: async (id: number) => {
    const r = await http.get<UserProfileResponseDTO>(`/userprofile/ViewProfile/id/${id}`);
    return r.data;
  },
  update: async (id: number, b: UserProfileUpdateDTO) => {
    const r = await http.put<UserProfileUpdateDTO>(`/userprofile/UpdateProfile/${id}`, b);
    return r.data;
  },
};