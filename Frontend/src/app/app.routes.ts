import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Inscription } from './pages/inscription/inscription';
import { Home } from './pages/home/home';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  {
    path: '',
    component: Login,
    title: 'Connexion',
  },
  {
    path: 'inscription',
    component: Inscription,
    title: 'Inscription',
  },
  
  {
    path: 'home',
    component: Home,
    title: 'Accueil',
    canActivate: [authGuard]
  },
];
