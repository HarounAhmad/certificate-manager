import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {TableLazyLoadEvent} from "primeng/table";
import {FilterMetadata} from "primeng/api";

@Injectable({
  providedIn: 'root'
})
export class AuditLogService {
  apiUrl = 'http://localhost:8080/api/v1/audit-logs';

  constructor(private http: HttpClient) { }

  getAuditLogs(page: number, size: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);
    return this.http.get<any>(`${this.apiUrl}/get`, { headers });
  }


  lazyLoadAuditLogs(event: TableLazyLoadEvent) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);

    const filterPayload: { [key: string]: any } = {};
    if (event.filters) {
      Object.keys(event.filters).forEach(field => {
        const filterValue = event.filters![field];
        const metas = Array.isArray(filterValue) ? filterValue : [filterValue];

        filterPayload[field] = metas.map((meta: any) => {
          if (field === 'timestamp' && Array.isArray(meta.value)) {
            // Expecting [from, to] from the calendar range picker
            return {
              from: meta.value[0],
              to: meta.value[1],
              matchMode: meta.matchMode
            };
          } else {
            return {
              value: meta.value,
              matchMode: meta.matchMode
            };
          }
        });
      });
    }

    const body = {
      page: Math.floor(event.first! / event.rows!), // always use Math.floor for safety
      size: event.rows,
      filters: filterPayload
    };

    return this.http.post<any>(`${this.apiUrl}/get`, body, { headers });
  }


  getToken(): string {
    return localStorage.getItem('jwt_token') || '';
  }
}
