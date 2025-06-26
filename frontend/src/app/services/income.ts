import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, retry, catchError } from 'rxjs';
import { throwError } from 'rxjs/internal/observable/throwError';
import { environment } from '../../environments/environment';
import { Income } from '../models/incomes.model';

@Injectable({
  providedIn: 'root'
})
export class IncomeService {

 private readonly userUrl = `${environment.apiUrl}user`;
  private readonly retryCount = 2;

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('auth_token');
    if (!token) {
      throw new Error('Token de autenticação não encontrado');
    }
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }


  getAllIncomes(userId: string): Observable<Income[]> {
    return this.http.get<Income[]>(
      `${this.userUrl}/${userId}/incomes`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      retry(this.retryCount),
      catchError(this.handleError)
    );
  }

  getIncomeById(incomeId: string): Observable<Income> {
    return this.http.get<Income>(
      `${this.userUrl}/incomes/${incomeId}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }


  createIncome(userId: string, income: Omit<Income, 'id'>): Observable<Income> {
    return this.http.post<Income>(
      `${this.userUrl}/${userId}/incomes`,
      income,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }


  updateIncome(incomeId: string, income: Partial<Income>): Observable<Income> {
    return this.http.put<Income>(
      `${this.userUrl}/incomes/${incomeId}`,
      income,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }


  deleteIncome(incomeId: string): Observable<void> {
    return this.http.delete<void>(
      `${this.userUrl}/incomes/${incomeId}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Erro desconhecido ao processar a receita';
    
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      switch (error.status) {
        case 400:
          errorMessage = 'Dados inválidos: ' + (error.error.message || JSON.stringify(error.error));
          break;
        case 401:
          errorMessage = 'Autenticação expirada. Por favor, faça login novamente.';
          break;
        case 404:
          errorMessage = 'Receita não encontrada.';
          break;
        case 500:
          errorMessage = 'Erro interno no servidor. Tente novamente mais tarde.';
          break;
        default:
          errorMessage = `Erro ${error.status}: ${error.message}`;
      }
    }
    
    console.error('IncomeService error:', errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
