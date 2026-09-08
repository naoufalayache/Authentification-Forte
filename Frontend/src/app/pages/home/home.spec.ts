import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Home } from './home';
import { AuthService } from '../../services/auth.service';
import { CoockieService } from '../../services/coockie.service';
import { Router } from '@angular/router';

describe('Home', () => {
  let component: Home;
  let fixture: ComponentFixture<Home>;

  let authServiceMock: {
    logout: ReturnType<typeof vi.fn>;
  };

  let coockieServiceMock: {
    getPseudoFromToken: ReturnType<typeof vi.fn>;
  };

  let routerMock: {
    navigate: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {
    authServiceMock = {
      logout: vi.fn(),
    };

    coockieServiceMock = {
      getPseudoFromToken: vi.fn(),
    };

    routerMock = {
      navigate: vi.fn().mockResolvedValue(true),
    };

    await TestBed.configureTestingModule({
      imports: [Home],
      providers: [
        {
          provide: AuthService,
          useValue: authServiceMock,
        },
        {
          provide: CoockieService,
          useValue: coockieServiceMock,
        },
        {
          provide: Router,
          useValue: routerMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Home);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display pseudo', () => {
    coockieServiceMock.getPseudoFromToken.mockReturnValue('Naoufal');

    fixture.detectChanges();

    const html: HTMLElement = fixture.nativeElement;

    expect(html.textContent).toContain('Bonjour Naoufal');
  });

  it('should display welcome message when pseudo is null', () => {
    coockieServiceMock.getPseudoFromToken.mockReturnValue(null);

    fixture.detectChanges();

    const html: HTMLElement = fixture.nativeElement;

    expect(html.textContent).toContain("Bienvenue sur l'application.");
  });

  it('should logout and redirect', () => {
    component.logout();

    expect(authServiceMock.logout).toHaveBeenCalled();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
  });
});
