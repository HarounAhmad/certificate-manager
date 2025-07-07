import { Component } from '@angular/core';
import {Password} from "primeng/password";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../../service/auth.service";
import {InputText} from "primeng/inputtext";
import {ButtonDirective} from "primeng/button";

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
export class LoginComponent {


  password: string = '';
  username: string = '';


  constructor(
    private authService: AuthService,
  ) {
  }

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        // Handle successful login, e.g., redirect to dashboard
      },
      error: (error) => {
        console.error('Login failed', error);
        // Handle login error, e.g., show an error message
      }
    });
  }



}
