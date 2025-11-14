import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from '../../../core/services/http.service';
import { Secretaria, SecretariaRequest } from '../../../core/models/secretaria.model';

@Injectable({
  providedIn: 'root'
})
export class SecretariaService {
  private readonly endpoint = '/api/secretarias';

  constructor(private httpService: HttpService) {}

  getAll(): Observable<Secretaria[]> {
    return this.httpService.get<Secretaria[]>(this.endpoint);
  }

  getById(id: number): Observable<Secretaria> {
    return this.httpService.get<Secretaria>(`${this.endpoint}/${id}`);
  }

  create(secretaria: SecretariaRequest): Observable<Secretaria> {
    return this.httpService.post<Secretaria>(this.endpoint, secretaria);
  }

  update(id: number, secretaria: SecretariaRequest): Observable<Secretaria> {
    return this.httpService.put<Secretaria>(`${this.endpoint}/${id}`, secretaria);
  }

  delete(id: number): Observable<void> {
    return this.httpService.delete<void>(`${this.endpoint}/${id}`);
  }

  getExportCsvUrl(): string {
    return this.httpService.getFullUrl(`${this.endpoint}/export/csv`);
  }
}
