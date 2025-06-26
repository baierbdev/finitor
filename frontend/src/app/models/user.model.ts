import { Expense } from "./expense.model";
import { Income } from "./incomes.model";

export interface User {
    id?: string;
    username?: string;
    email: string;
    password: string;
    baseSalary?: number;
    expenses?: Expense[]
    incomes?: Income[]
}