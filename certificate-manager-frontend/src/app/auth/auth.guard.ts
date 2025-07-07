import {ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router} from '@angular/router';
import {inject, Injectable} from '@angular/core';
import {AuthService} from "../service/auth.service";

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const allowedRoles = route.data['roles'] as string[] | undefined;
    const userRole = this.auth.getRole();
    console.log(allowedRoles);
    if (this.auth.isLoggedIn() && allowedRoles?.includes(userRole)) {
      return true;
      console.log(`User role ${userRole} is allowed to access this route.`);
    }
    console.log(`User role ${userRole} is not allowed to access this route. Redirecting to login.`);

    this.auth.logout();
    this.router.navigate(['/login']);
    return false;
  }
}
