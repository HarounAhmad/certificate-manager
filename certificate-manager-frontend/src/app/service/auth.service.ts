import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {



  constructor(private http: HttpClient) {}

  private tokenKey = 'jwt_token';
  apiUrl = 'http://localhost:8080/api/v1';

  login(username: string, password: string): Observable<any> {
    return this.http.post<{token: string}>(this.apiUrl +  '/auth/login', { username, password }).pipe(
      tap(res => localStorage.setItem(this.tokenKey, res.token))
    );
  }

  getRole(): string {
    const token = localStorage.getItem(this.tokenKey);
    if (!token) return '';

    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.roles && payload.roles.length > 0 ? payload.roles[0] : '';
  }

  isTokenExpired(): boolean {
    const token = localStorage.getItem(this.tokenKey);
    if (!token) return true;

    const payload = JSON.parse(atob(token.split('.')[1]));
    const expiry = payload.exp * 1000; // JWT exp is in seconds

    return Date.now() > expiry;
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem(this.tokenKey) && !this.isTokenExpired();
  }
  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }


  getUsername(): string {
    const token = localStorage.getItem(this.tokenKey);
    if (!token) return '';
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.sub;
  }

}
