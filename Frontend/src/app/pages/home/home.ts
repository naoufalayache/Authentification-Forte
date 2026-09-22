import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { CoockieService } from '../../services/coockie.service';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { MessageFromResponse, User } from '../../models/model';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  constructor(
    private authService: AuthService,
    private userService: UserService,
    private coockieService: CoockieService,
    private router: Router,
  ) {}

  protected pseudo: string | null = null;
  protected page: number | null = null;
  protected size: number = 20;
  users = signal<User[]>([]);
  protected messageEmpty: MessageFromResponse = {bool: false,str: 'Aucun utilisateur trouvé'};

  ngOnInit(): void {
    this.pseudo = this.coockieService.getPseudoFromToken();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']).then((success) => {
      console.log('Redirection :', success);
    });
  }

  handleSearch(search: string): void {
    const value = search.trim();

    if (!value) {
      return;
    }

    this.page = this.page === null ? 0 : this.page + 1;

    this.userService.getUserByEmail(value,this.page,this.size).subscribe({
      next:(response: User[]) => {
        console.log('Réponse reçue :', response);
        console.log('Est un tableau :', Array.isArray(response));
        this.users.set(response);
        console.log('Valeur du signal :', this.users());
      },
      error: (error) => {
        this.users.set([]);
        this.messageEmpty.bool = true;
      }
    })
  }
}
