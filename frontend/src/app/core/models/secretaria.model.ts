export interface Secretaria {
  id?: number;
  nome: string;
  sigla: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface SecretariaRequest {
  nome: string;
  sigla: string;
}
