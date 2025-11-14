import { Secretaria } from './secretaria.model';

export interface Servidor {
  id?: number;
  nome: string;
  email: string;
  dataNascimento: string;
  idade?: number;
  secretaria: Secretaria;
  createdAt?: string;
  updatedAt?: string;
}

export interface ServidorRequest {
  nome: string;
  email: string;
  dataNascimento: string;
  secretariaId: number;
}
