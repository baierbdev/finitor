import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  constructor(
    private auth: Auth,
    private router: Router
  ) { }

  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.email]),
    password: new FormControl('', [Validators.minLength(8)])
  })
  loginUser() {
    const loginValues = this.loginForm.value;

    this.auth.login(loginValues.email, loginValues.password).subscribe({
      next: (response) => {        
        this.router.navigate(['/home'])
      },
      error: (err) => {
        alert('Erro ao realizar login');
        console.log(err);
      }
    });
  }
}
