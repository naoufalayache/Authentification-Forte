import { Injectable } from '@angular/core';
import { Config } from '../models/model';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ConfigService {
  private config!: Config;

  constructor(private http: HttpClient) {}

  load(): Promise<void> {
    return firstValueFrom(this.http.get<Config>('/config.json')).then((config) => {
      this.config = config;
    });
  }

  get apiUrl(): string {
    return this.config.apiUrl;
  }
}
