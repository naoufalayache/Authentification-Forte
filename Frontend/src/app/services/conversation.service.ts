import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { Conversation, CreateConv, Response } from '../models/model';

@Injectable({
  providedIn: 'root',
})
export class ConversationService {
  constructor(
    private http: HttpClient,
    private configService: ConfigService,
  ) {}

  getConversations(userId: number, page: number, size: number) {
    const params = new HttpParams()
      .set('userId', userId)
      .set('page', page)
      .set('size', size);
    return this.http.get<Conversation[]>(`${this.configService.apiUrl}/conversation`,{params})
  }

  create(request: CreateConv){
    return this.http.post<Response>(`${this.configService.apiUrl}/create`,request);
  }
}
