import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';

import { Inscription } from './inscription';

describe('Inscription', () => {
  let component: Inscription;
  let fixture: ComponentFixture<Inscription>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Inscription],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(Inscription);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create login form', () => {
    expect(component.inscriptionForm).toBeTruthy();
  });

  it('should have email and password+confirm password fields', () => {
    expect(component.inscriptionForm.get('email')).toBeTruthy();
    expect(component.inscriptionForm.get('password')).toBeTruthy();
    expect(component.inscriptionForm.get('confirmPassword')).toBeTruthy();
  });

  it('should be valid with good credentials', () => {
    component.inscriptionForm.setValue({
      email: 'user@gmail.com',
      password: 'password',
      confirmPassword : 'password',
    });

    expect(component.inscriptionForm.valid).toBe(true);
  });
});
