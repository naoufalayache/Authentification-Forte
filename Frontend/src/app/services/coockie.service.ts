import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CoockieService {
  setToken(token: string): void {
    document.cookie = `access_token=${token}; path=/; SameSite=Lax`;
  }

  getToken(): string | null {
    const coockies = document.cookie.split(';');

    for (const coockie of coockies){
      const [name, value] = coockie.trim().split('=');
      if (name === 'access_token'){
        return value;
      }
    }
    return null;
  }

  deleteToken(): void {
    document.cookie = 'access_token=; path=/; max-age=0';
  }
}
