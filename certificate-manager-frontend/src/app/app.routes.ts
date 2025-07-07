import { Routes } from '@angular/router';
import {LoginComponent} from "./components/auth/login/login.component";
import {DashboardComponent} from "./components/main/dashboard/dashboard.component";
import {RoleGuard} from "./auth/auth.guard";
import {MainComponent} from "./components/main/main/main.component";
import {SettingsComponent} from "./components/main/settings/settings.component";
import {AuditLogComponent} from "./components/main/audit-log/audit-log.component";
import {
  CertificateManagementComponent
} from "./components/main/certificate-management/certificate-management.component";
export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'app',
    component: MainComponent,
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [RoleGuard],
        data: { roles: ['SYSADMIN'] }
      },
      {
        path: 'settings',
        component: SettingsComponent,
        canActivate: [RoleGuard],
        data: { roles: ['SYSADMIN'] }
      },
      {
        path: 'auditlog',
        component: AuditLogComponent,
        canActivate: [RoleGuard],
        data: { roles: ['SYSADMIN'] }
      },
      {
        path: 'certificates',
        component: CertificateManagementComponent,
        canActivate: [RoleGuard],
        data: { roles: ['SYSADMIN'] }
      }
    ]
  }
];
