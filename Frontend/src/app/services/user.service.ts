import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { User } from '../models/model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private http: HttpClient,
    private configService: ConfigService,
  ) {}

  getUserByEmail(email: string, page: number, size: number) {
    const params = new HttpParams()
      .set('email', email)
      .set('page', page)
      .set('size', size);
    return this.http.get<User[]>(`${this.configService.apiUrl}/user`,{params})
  }
}
