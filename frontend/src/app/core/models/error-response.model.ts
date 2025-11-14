export interface ErrorResponse {
  timestamp: string;
  message: string;
  errors: { [key: string]: string };
}
