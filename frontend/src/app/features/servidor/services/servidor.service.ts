import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from '../../../core/services/http.service';
import { Servidor, ServidorRequest } from '../../../core/models/servidor.model';

@Injectable({
  providedIn: 'root'
})
export class ServidorService {
  private readonly endpoint = '/api/servidores';

  constructor(private httpService: HttpService) {}

  getAll(): Observable<Servidor[]> {
    return this.httpService.get<Servidor[]>(this.endpoint);
  }

  getById(id: number): Observable<Servidor> {
    return this.httpService.get<Servidor>(`${this.endpoint}/${id}`);
  }

  create(servidor: ServidorRequest): Observable<Servidor> {
    return this.httpService.post<Servidor>(this.endpoint, servidor);
  }

  update(id: number, servidor: ServidorRequest): Observable<Servidor> {
    return this.httpService.put<Servidor>(`${this.endpoint}/${id}`, servidor);
  }

  delete(id: number): Observable<void> {
    return this.httpService.delete<void>(`${this.endpoint}/${id}`);
  }

  getExportCsvUrl(): string {
    return this.httpService.getFullUrl(`${this.endpoint}/export/csv`);
  }
}
