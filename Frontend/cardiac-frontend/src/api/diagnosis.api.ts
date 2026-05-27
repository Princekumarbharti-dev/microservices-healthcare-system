import { http } from "./http";
import type { Diagnosis } from "../types/diagnosis";

export type DiagnosisFilters = Partial<{
  gender: string;
  age: string;
  bp: string;
  pain_type: string;
}>;

export const diagnosisApi = {
  list: async (f: DiagnosisFilters) => {
    const p = new URLSearchParams();
    Object.entries(f).forEach(([k, v]) => {
      if (v !== undefined && v !== null && String(v).trim() !== "") p.set(k, String(v).trim());
    });
    const qs = p.toString();
    const url = qs ? `/diagnosis?${qs}` : "/diagnosis";
    const r = await http.get<Diagnosis[]>(url);
    return r.data;
  },
};