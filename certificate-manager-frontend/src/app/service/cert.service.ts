import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CertService {

  constructor(private http: HttpClient) {}

  uploadCert() {
    return this.http.post('/api/certificates/upload', {});
  }}
