export interface LoginRequestDTO {
    email: string;
    password: string;
  }
  
  export interface LoginResponseDTO {
    token: string;
    userId: number;
    email: string;
    role: string;
  }
  
  export interface ValidateResponseDTO {
    valid: boolean;
    userId: number | null;
    email: string | null;
    role: string | null;
  }
  
  export interface UpdatePasswordRequestDTO {
    oldPassword: string;
    newPassword: string;
    confirmPassword: string;
  }


  export type ForgotPasswordRequestDTO = {
    email: string;
    newPassword: string;
    confirmPassword: string;
  };