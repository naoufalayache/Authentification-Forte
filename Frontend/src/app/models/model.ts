export interface User {
  id?: number;
  email: string;
  enabled?: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  confirmPassword: string;
}

export interface AuthResponse {
  token: string;
}

export interface MessageFromResponse{
  bool: boolean;
  str: string;
}

export interface Config {
  apiUrl: string;
}

export interface Message {
  id: number;
  value: string;
  idUser: number;
  idConversation: number;
  createdAt: string;
}

export interface MessageSended {
  value: string;
  idUser: number;
  idConversation: number;
  idMessage?: number;
  createdAt?: string;
}

export interface Response {
  httpStatus: string;
  message: string;
}

export interface DeleteConv {
  idMessage: number
  idConversation: number
  idUser: number
}

export interface Conversation {
  nmbrPeople: number
  nom: String
  createdAt: string
}

export interface CreateConv {
  usersId: number[]
  name: String
}