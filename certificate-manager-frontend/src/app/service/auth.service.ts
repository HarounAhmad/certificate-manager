import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {



  constructor(private http: HttpClient) {}


  login(username: string, password: string) {
    const authHeader = 'Basic ' + btoa(`${username}:${password}`);
    const headers = new HttpHeaders({ 'Authorization': authHeader });

    return this.http.get('http://localhost:8080/api/auth/whoami', { headers });
  }

}
