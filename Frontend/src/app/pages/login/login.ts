import { Component, signal } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AuthResponse, LoginRequest } from '../../models/model';
import { CoockieService } from '../../services/coockie.service';

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
    private coockieService: CoockieService,
    private router: Router,
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
      password: this.loginForm.value.password!,
    };

    this.authService.login(request).subscribe({
      next: (response: AuthResponse) => {
        this.coockieService.setToken(response.token);
        this.niveauMessage.set('success');
        this.message.set('Vous vous êtes bien connecté ! Redirection en cours...');
        setTimeout(() => {
          this.router.navigate(['/home']);
        }, 3000);
      },
      error: (error) => {
        this.niveauMessage.set('error');
        this.message.set(error.error?.message ?? "Erreur lors de l'inscription");
      },
    });
  };
}
