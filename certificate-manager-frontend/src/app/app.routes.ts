import { Routes } from '@angular/router';
import {LoginComponent} from "./components/auth/login/login.component";
import {DashboardComponent} from "./components/main/dashboard/dashboard.component";
import {authGuard} from "./auth/auth.guard";
export const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard]
  }
];
