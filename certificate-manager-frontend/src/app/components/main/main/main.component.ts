import { Component } from '@angular/core';
import {NavigationBarComponent} from "../../blocks/navigation-bar/navigation-bar.component";
import {DashboardComponent} from "../dashboard/dashboard.component";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-main',
  imports: [
    NavigationBarComponent,
    DashboardComponent,
    RouterOutlet
  ],
  templateUrl: './main.component.html',
  standalone: true,
  styleUrl: './main.component.scss'
})
export class MainComponent {

}
