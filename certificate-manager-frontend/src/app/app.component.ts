import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AuthService} from "./service/auth.service";
import {LoginComponent} from "./components/auth/login/login.component";
import {MainComponent} from "./components/main/main/main.component";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoginComponent, MainComponent, NgIf],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'certificate-manager-frontend';


  constructor(
    private authService: AuthService
  ) {
  }

}
