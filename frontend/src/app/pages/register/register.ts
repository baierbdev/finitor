import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Auth } from '../../services/auth';
import {  Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {

  constructor(
    private auth: Auth,
    private router: Router
  ) { }


  registerForm: FormGroup = new FormGroup({
    baseSalary: new FormControl(''),
    username: new FormControl('', [Validators.required, Validators.minLength(3)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    email: new FormControl('', [Validators.email, Validators.required])
  })

  registerUser() {
    const formValue = this.registerForm.value;
    if (this.registerForm.invalid) {
      alert('Por favor, preencha todos os campos corretamente');
      return;
    }

    this.auth.register(formValue).subscribe({
      next: (response) => {
        alert("Usuário cadastrado");
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert('Erro ao realizar registro');
        console.log(err);
      }
    });
  }
}
