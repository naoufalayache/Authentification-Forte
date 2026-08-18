import { isPlatformBrowser } from '@angular/common';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CoockieService {
  platformId = inject(PLATFORM_ID);

  setToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      document.cookie = `access_token=${token}; path=/; SameSite=Lax`;
    }
  }

  getToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) {
      return null;
    }

    const coockies = document.cookie.split(';');

    for (const coockie of coockies) {
      const [name, value] = coockie.trim().split('=');
      if (name === 'access_token') {
        return value;
      }
    }
    return null;
  }

  getPseudoFromToken(): string | null {
    const token = this.getToken();

    if (!token) {
      return null;
    }

    try {
      const payload = token.split('.')[1];
      const decodedPayload = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));

      return decodedPayload.sub.split('@')[0] ?? null;
    } catch {
      return null;
    }
  }

  deleteToken(): void {
    if (isPlatformBrowser(this.platformId)) {
      document.cookie = 'access_token=; path=/; max-age=0';
    }
  }
}
