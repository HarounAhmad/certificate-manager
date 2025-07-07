import {Component, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {Menubar} from "primeng/menubar";
import {Badge} from "primeng/badge";
import {NgClass, NgIf} from "@angular/common";
import {Avatar} from "primeng/avatar";
import {Ripple} from "primeng/ripple";
import {InputText} from "primeng/inputtext";
import {DropdownModule} from "primeng/dropdown";
import {SplitButton} from "primeng/splitbutton";
import {AuthService} from "../../../service/auth.service";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-navigation-bar',
  imports: [
    Menubar,
    Badge,
    NgClass,
    Avatar,
    Ripple,
    InputText,
    NgIf,
    DropdownModule,
    SplitButton,
    RouterLink
  ],
  templateUrl: './navigation-bar.component.html',
  standalone: true,
  styleUrl: './navigation-bar.component.scss'
})
export class NavigationBarComponent implements OnInit{
  menuItems: MenuItem[] | undefined;
  userActions: MenuItem[] | undefined;
  username: string | undefined;


  constructor(
    private authService:AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.menuItems = [
      {
        label: 'Dashboard',
        icon: 'pi pi-fw pi-home',
        routerLink: '/app/dashboard'
      },
      {
        label: 'Certificates',
        icon: 'pi pi-fw pi-file',
        routerLink: '/app/certificates'
      },
      {
        label: 'Logs',
        icon: 'pi pi-fw pi-cog',
        routerLink: '/app/auditlog'
      },
      {
        label: 'Settings',
        icon: 'pi pi-fw pi-cog',
        routerLink: '/app/settings'
      }
    ];

    this.userActions = [
      {
        label: 'Profile',
        icon: 'pi pi-fw pi-user',
        routerLink: '/app/profile'
      },
      {
        label: 'Logout',
        icon: 'pi pi-fw pi-sign-out',
        command: () => this.logout()
      }
    ];

    this.username = this.authService.getUsername()
  }


  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
