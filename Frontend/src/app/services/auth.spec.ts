import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { ConfigService } from './config.service';
import { CoockieService } from './coockie.service';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/model';

describe('Auth', () => {
  let service: AuthService;
  let httpTesting: HttpTestingController;
  let configService: ConfigService;

  let apiUrl: string;

  const coockieServiceMock = {
    getToken: vi.fn(),
    deleteToken: vi.fn(),
  };

  beforeEach(() => {
    TestBed.resetTestingModule();
    vi.resetAllMocks();

    TestBed.configureTestingModule({
      providers: [
        AuthService,
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: ConfigService,
          useValue: { apiUrl },
        },
        {
          provide: CoockieService,
          useValue: coockieServiceMock,
        },
      ],
    });
    configService = TestBed.inject(ConfigService);
    apiUrl = configService.apiUrl;

    service = TestBed.inject(AuthService);
    httpTesting = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('doit envoyer les données d’inscription et retourner la réponse', () => {
    const request = {
      email: 'test@example.com',
      password: 'TestPassword123!',
    } as RegisterRequest;

    const response = {
      token: 'token-inscription',
    } as AuthResponse;

    const onResponse = vi.fn();

    service.register(request).subscribe(onResponse);

    const req = httpTesting.expectOne(`${apiUrl}/auth/register`);

    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(request);

    req.flush(response);

    expect(onResponse).toHaveBeenCalledExactlyOnceWith(response);
  });

  it('doit envoyer les identifiants et retourner la réponse', () => {
    const request = {
      email: 'test@example.com',
      password: 'TestPassword123!',
    } as LoginRequest;

    const response = {
      token: 'token-connexion',
    } as AuthResponse;

    const onResponse = vi.fn();

    service.login(request).subscribe(onResponse);

    const req = httpTesting.expectOne(`${apiUrl}/auth/login`);

    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(request);

    req.flush(response);

    expect(onResponse).toHaveBeenCalledExactlyOnceWith(response);
  });

  it("doit transmettre l'erreur lorsque la connexion est refusée", () => {
    const request = {
      email: 'test@example.com',
      password: 'mauvais-mot-de-passe',
    } as LoginRequest;

    const onSuccess = vi.fn();
    const onError = vi.fn();

    service.login(request).subscribe({
      next: onSuccess,
      error: onError,
    });

    const req = httpTesting.expectOne(`${apiUrl}/auth/login`);

    req.flush({ message: 'Identifiants incorrects' }, { status: 401, statusText: 'Unauthorized' });

    expect(onSuccess).not.toHaveBeenCalled();
    expect(onError).toHaveBeenCalledTimes(1);

    const error = onError.mock.calls[0][0] as HttpErrorResponse;
    expect(error.status).toBe(401);
  });

  it('doit supprimer le token lors de la déconnexion', () => {
    service.logout();

    expect(coockieServiceMock.deleteToken).toHaveBeenCalledTimes(1);
    httpTesting.expectNone(() => true);
  });

  it('doit vérifier le token en l’envoyant dans le header Authorization', () => {
    coockieServiceMock.getToken.mockReturnValue('mon-token');

    const response = { valid: true };
    const onResponse = vi.fn();

    service.checkToken().subscribe(onResponse);

    const req = httpTesting.expectOne(`${apiUrl}/auth/check`);

    expect(coockieServiceMock.getToken).toHaveBeenCalledTimes(1);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mon-token');

    req.flush(response);

    expect(onResponse).toHaveBeenCalledExactlyOnceWith(response);
  });

  it("doit transmettre l'erreur lorsque le token est refusé", () => {
    coockieServiceMock.getToken.mockReturnValue('token-invalide');

    const onSuccess = vi.fn();
    const onError = vi.fn();

    service.checkToken().subscribe({
      next: onSuccess,
      error: onError,
    });

    const req = httpTesting.expectOne(`${apiUrl}/auth/check`);

    req.flush({ message: 'Token invalide' }, { status: 401, statusText: 'Unauthorized' });

    expect(onSuccess).not.toHaveBeenCalled();
    expect(onError).toHaveBeenCalledTimes(1);

    const error = onError.mock.calls[0][0] as HttpErrorResponse;
    expect(error.status).toBe(401);
  });
});
