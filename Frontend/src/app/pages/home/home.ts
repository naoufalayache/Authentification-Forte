import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CoockieService } from '../../services/coockie.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  constructor(
    private authService: AuthService,
    private coockieService: CoockieService,
    private router: Router,
  ) {}

  protected pseudo: string | null = null;

  ngOnInit(): void {
    this.pseudo = this.coockieService.getPseudoFromToken();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']).then((success) => {
      console.log('Redirection :', success);
    });
  }
}
