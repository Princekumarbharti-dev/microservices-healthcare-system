import { http } from "./http";
import type { Bookmark } from "../types/bookmark";

export const bookmarkApi = {
  add: async (diagnosisId: string) => {
    const r = await http.post<Bookmark>("/bookmark", { diagnosisId });
    return r.data;
  },
  mine: async () => {
    const r = await http.get<Bookmark[]>("/bookmark");
    return r.data;
  },
  remove: async (id: number) => {
    await http.delete(`/bookmark/${id}`);
  },
};