import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly userUrl: string = `${environment.apiUrl}user`;

  constructor(
    private http: HttpClient
  ) { }


  getUserByToken() {
    return this.http.get<User>(this.userUrl)
      .pipe(
        tap(user =>
          localStorage.setItem("currentUser", JSON.stringify(user))
        )
      );
  }
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('auth_token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }
  updatePassword(id: string, currentPassword: string, newPassword: string): Observable<any> {
    return this.http.put(`${this.userUrl}/${id}/updatePassword`,
      { currentPassword, newPassword },
      { headers: this.getAuthHeaders() }
    );
  }
  deleteUser(id: string): Observable<any> {
    return this.http.delete(`${this.userUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

    getCurrentUser(): User | null {
    const userData = localStorage.getItem('currentUser');
    return userData ? JSON.parse(userData) : null;
  }

  getUserId(): string {
    const user = this.getCurrentUser();
    if (!user || !user.id) {
      throw new Error('Usuário não autenticado ou ID não disponível');
    }
    return user.id;
  }
}
