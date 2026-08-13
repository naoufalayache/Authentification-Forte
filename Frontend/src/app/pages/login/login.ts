import { Component, signal } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/model';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  loginForm;
  protected niveauMessage = signal<string>('');
  protected message = signal<string>('');

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit = (): void => {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }
    
    const request: LoginRequest = {
      email: this.loginForm.value.email!,
      password: this.loginForm.value.password!
    }

    this.authService.login(request).subscribe({
      next: () => {
        this.niveauMessage.set('success');
        this.message.set('Vous vous êtes bien connecté !');
      },
      error: (error) => {
        this.niveauMessage.set('error');
        this.message.set(error.error?.message ?? "Erreur lors de l'inscription");
      },
    });
    
  };
}
