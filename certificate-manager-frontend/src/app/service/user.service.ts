import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserResponse} from "../model/user-response";
import {Observable} from "rxjs";
import {Role} from "../model/role";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient
  ) { }

  apiUrl = 'http://localhost:8080/api/v1/users';


  getAllUsers(): Observable<UserResponse[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);
    return this.http.get<UserResponse[]>(`${this.apiUrl}/all` , { headers });
  }

  getToken(): string {
    return localStorage.getItem('jwt_token') || '';
  }

  getUserRoleOptions(): Observable<Role[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);
    return this.http.get<Role[]>(`${this.apiUrl}/roles`, { headers });
  }

  deleteUser(user: UserResponse) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);
    return this.http.delete(`${this.apiUrl}/${user.id}`, { headers });
  }

  updateUser(user: UserResponse) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);
    return this.http.put<UserResponse>(`${this.apiUrl}/${user.id}`, user, { headers });
  }
}
