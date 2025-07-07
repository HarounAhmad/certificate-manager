import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuditLogService {
  apiUrl = 'http://localhost:8080/api/v1/audit-logs';

  constructor(private http: HttpClient) { }

  getAuditLogs(page: number, size: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<any>(`${this.apiUrl}/get`, { headers, params });
  }

  getToken(): string {
    return localStorage.getItem('jwt_token') || '';
  }
}
