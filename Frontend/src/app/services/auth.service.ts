import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/model';
import { ConfigService } from './config.service';
import { CoockieService } from './coockie.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private coockieService: CoockieService,
    private router: Router,
  ) {}

  register(request: RegisterRequest) {
    return this.http.post<AuthResponse>(`${this.configService.apiUrl}/auth/register`, request);
  }

  login(request: LoginRequest) {
    return this.http.post<AuthResponse>(`${this.configService.apiUrl}/auth/login`, request);
  }

  logout(): void{
    this.coockieService.deleteToken();
  }

  checkToken() {
    const token = this.coockieService.getToken();

    return this.http.get(`${this.configService.apiUrl}/auth/check`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}
