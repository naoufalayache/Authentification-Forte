import { TestBed } from '@angular/core/testing';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';

import { authGuard } from './auth-guard';
import { AuthService } from '../services/auth.service';
import { Observable, of, throwError } from 'rxjs';

describe('authGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => authGuard(...guardParameters));

  let authServiceMock: {
    checkToken: ReturnType<typeof vi.fn>;
    logout: ReturnType<typeof vi.fn>;
  };

  let routerMock: {
    createUrlTree: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {
    authServiceMock = {
      checkToken: vi.fn(),
      logout: vi.fn(),
    };

    routerMock = {
      createUrlTree: vi.fn(),
    };

    await TestBed.configureTestingModule({
      providers: [
        {
          provide: AuthService,
          useValue: authServiceMock,
        },
        {
          provide: Router,
          useValue: routerMock,
        },
      ],
    }).compileComponents();
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });

  it('should have a good token', () => {
    authServiceMock.checkToken.mockReturnValue(of(true));
    expect(authServiceMock.logout).not.toHaveBeenCalled();
  });

  it('should have a wrong token', () => {
    authServiceMock.checkToken.mockReturnValue(throwError(() => new Error('Unauthorized')));

    const result = executeGuard(
      {} as ActivatedRouteSnapshot,
      {} as RouterStateSnapshot,
    ) as Observable<boolean | UrlTree>;

    result.subscribe();

    expect(authServiceMock.logout).toHaveBeenCalled();

    expect(routerMock.createUrlTree).toHaveBeenCalledWith(['/']);
  });
});
