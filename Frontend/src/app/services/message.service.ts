import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { DeleteConv, Message, MessageSended, Response } from '../models/model';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  constructor(
    private http: HttpClient,
    private configService: ConfigService,
  ) {}

  upsertMessages(request: MessageSended) {
    return this.http.post<Response>(`${this.configService.apiUrl}/message/upsert`, request);
  }

  getMessages(conversationId: number, page: number, size: number) {
    const params = new HttpParams()
      .set('conversationId', conversationId)
      .set('page', page)
      .set('size', size);
      return this.http.get<Message[]>(`${this.configService.apiUrl}/message`,{params})
  }

  deleteMessage(request: DeleteConv){
    return this.http.delete<Response>(`${this.configService.apiUrl}/message`, {body : request})
  }
}
