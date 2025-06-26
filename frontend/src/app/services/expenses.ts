import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { Expense } from '../models/expense.model';
import { Transaction } from '../models/Transaction.model';

@Injectable({
  providedIn: 'root'
})
export class ExpensesServices {
  private readonly userUrl: string = `${environment.apiUrl}user/`;
  private readonly retryCount = 2;

  constructor(private http: HttpClient){}



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

  getAllExpenses(userId: string): Observable<Expense[]> {
    return this.http.get<Expense[]>(
      `${this.userUrl}${userId}/expenses`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      retry(this.retryCount),
      catchError(this.handleError)
    );
  }

  getExpenseById(expenseId: string): Observable<Expense> {
    return this.http.get<Expense>(
      `${this.userUrl}expenses/${expenseId}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }

  createExpense(userId: string, expense: Transaction): Observable<Expense> {
    return this.http.post<Expense>(
      `${this.userUrl}${userId}/expenses`,
      expense,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }

  updateExpense(expenseId: string, expense: Partial<Transaction>): Observable<Expense> {
    return this.http.put<Expense>(
      `${this.userUrl}expenses/${expenseId}`,
      expense,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }

  deleteExpense(expenseId: string): Observable<void> {
    return this.http.delete<void>(
      `${this.userUrl}expenses/${expenseId}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Erro desconhecido';
    
    if (error.error instanceof ErrorEvent) {
      // Erro do lado do cliente
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      // Erro do lado do servidor
      errorMessage = `Código: ${error.status}\nMensagem: ${error.message}`;
      
      // Tratamento específico para erros comuns
      if (error.status === 401) {
        errorMessage = 'Autenticação expirada. Por favor, faça login novamente.';
      } else if (error.status === 404) {
        errorMessage = 'Despesa não encontrada.';
      } else if (error.status === 400) {
        errorMessage = 'Dados inválidos: ' + (error.error.message || error.error);
      }
    }
    
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
