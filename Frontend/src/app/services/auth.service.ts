import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest, RegisterRequest } from '../models/model';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ){}

  register(request: RegisterRequest){
    return this.http.post(
      `${this.configService.apiUrl}auth/register`,
      request
    );
  }

  login(request: LoginRequest){
    return this.http.post(
      `${this.configService.apiUrl}auth/login`,
      request
    );
  }
}
