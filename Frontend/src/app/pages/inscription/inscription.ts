import { Component, signal } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthResponse, RegisterRequest } from '../../models/model';
import { AuthService } from '../../services/auth.service';
import { CoockieService } from '../../services/coockie.service';

@Component({
  selector: 'app-inscription',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './inscription.html',
  styleUrl: './inscription.css',
})
export class Inscription {
  inscriptionForm;
  protected niveauMessage = signal<string>('');
  protected message = signal<string>('');

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private coockieService: CoockieService,
    private router: Router
  ) {
    this.inscriptionForm = this.fb.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', [Validators.required]],
      },
      {
        validators: this.passwordMatchValidator,
      },
    );
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    return password === confirmPassword ? null : { passwordMismatch: true };
  }

  onSubmit = (): void => {
    if (this.inscriptionForm.invalid) {
      this.inscriptionForm.markAllAsTouched();
      return;
    }

    const request: RegisterRequest = {
      email: this.inscriptionForm.value.email!,
      password: this.inscriptionForm.value.password!,
      confirmPassword: this.inscriptionForm.value.confirmPassword!,
    };

    this.authService.register(request).subscribe({
      next: (response: AuthResponse) => {
        this.coockieService.setToken(response.token);
        this.niveauMessage.set('success');
        this.message.set('Vous vous êtes bien créé un compte !');
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
