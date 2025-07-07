import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CertService {

  constructor(private http: HttpClient) {}

  uploadCert() {
    return this.http.post('/api/certificates/upload', {});
  }}
