export type Gender = "MALE" | "FEMALE" | "Male" | "Female" | string;

export interface UserProfileRequestDTO {
  name: string;
  email: string;
  phone: string;
  age: number;
  gender: Gender;
  password: string;
  confirmPassword: string;
}

export interface UserProfileResponseDTO {
  name: string;
  email: string;
  phone: string;
  age: number;
  gender: Gender;
}

export interface UserProfileUpdateDTO {
  name: string;
  phone: string;
  age: number;
  gender: Gender;
}