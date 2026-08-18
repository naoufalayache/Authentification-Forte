import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.checkToken().pipe(
    map(() => true),
    catchError(()=> {
      authService.logout();
      return of(
        router.createUrlTree(['/'])
      )
    })
  )
};
