import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';
import { Income } from '../../models/incomes.model';
import { Expense } from '../../models/expense.model';
import { ExpensesServices } from '../../services/expenses'; 
import { IncomeService } from '../../services/income';
import { forkJoin } from 'rxjs';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class Home implements OnInit { 
   expenses: Expense[] = [];
  incomes: Income[] = [];
  currentUser: User | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private userService: UserService,
    private authService: Auth,
    private expenseService: ExpensesServices,
    private incomeService: IncomeService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }

    this.isLoading = true;
    this.error = null;

    this.userService.getUserByToken().subscribe({
      next: (user: User) => {
        this.currentUser = user;
        this.loadFinancialData();
      },
      error: (err) => {
        this.handleError(err);
      }
    });
  }

  loadFinancialData(): void {
  if (!this.currentUser?.id) { 
    this.error = 'Usuário não possui ID válido';
    this.isLoading = false;
    return;
  }

  this.isLoading = true;

  forkJoin({
    expenses: this.expenseService.getAllExpenses(this.currentUser.id),
    incomes: this.incomeService.getAllIncomes(this.currentUser.id)
  }).subscribe({
    next: ({ expenses, incomes }) => {
      this.expenses = expenses;
      this.incomes = incomes;
      this.isLoading = false;
      console.log(incomes);
    },
    error: (err) => {
      this.handleError(err);
    }
  });
}

  private handleError(err: any): void {
    this.isLoading = false;
    console.error('Erro:', err);
    
    if (err.status === 401) {
      this.authService.logout();
      this.router.navigate(['/login']);
    } else {
      this.error = 'Erro ao carregar dados. Tente novamente mais tarde.';
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  get availableBalance(): number {
    const totalIncomes = this.incomes.reduce((sum, income) => sum + income.amount, 0);
    const totalExpenses = this.expenses.reduce((sum, expense) => sum + expense.amount, 0);
    return (this.currentUser?.baseSalary || 0) + totalIncomes - totalExpenses;
  }
  hasNoTransactions(): boolean {
  return this.incomes.length === 0 && this.expenses.length === 0;
}
}