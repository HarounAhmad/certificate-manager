import {Component, OnInit} from '@angular/core';
import {Password} from "primeng/password";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../../service/auth.service";
import {InputText} from "primeng/inputtext";
import {ButtonDirective} from "primeng/button";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  imports: [
    Password,
    FormsModule,
    InputText,
    ButtonDirective
  ],
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{


  password: string = '';
  username: string = '';


  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/app/dashboard']);
    }
  }

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        this.router.navigate(['/app/dashboard']);
      },
      error: (error) => {
        console.error('Login failed', error);
        // Handle login error, e.g., show an error message
      }
    });
  }



}
