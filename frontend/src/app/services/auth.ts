import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { environment } from '../../environments/environment';
import { catchError, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Auth {

  private readonly TOKEN_KEY = 'auth_token';
  private readonly baseUrl = environment.apiUrl; 

  constructor(
    private http: HttpClient
  ) { }

  login(email: string, password: string): Observable<{token: string}> {
    return this.http.post<{token: string}>(
      `${this.baseUrl}auth/login`, 
      {email, password}
    ).pipe(
      tap(response => {
        this.setToken(response.token);
      })
    );
  }
  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}auth/register`, user);
  }

  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    console.log(localStorage.getItem(this.TOKEN_KEY))
    return localStorage.getItem(this.TOKEN_KEY);
  }

  hasToken(): boolean {
    return !!this.getToken();
  }
  

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;
    
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp > Date.now() / 1000;
    } catch {
      return false;
    }
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}