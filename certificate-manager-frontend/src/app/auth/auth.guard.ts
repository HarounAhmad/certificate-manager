import {ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router} from '@angular/router';
import {inject, Injectable} from '@angular/core';
import {AuthService} from "../service/auth.service";

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const allowedRoles = route.data['roles'] as string[];
    const userRole = this.auth.getRole();

    if (this.auth.isLoggedIn() && allowedRoles.includes(userRole)) {
      return true;
    }

    this.auth.logout();
    this.router.navigate(['/login']);
    return false;
  }
}
