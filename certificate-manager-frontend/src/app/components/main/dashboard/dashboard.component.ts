import { Component } from '@angular/core';
import {NavigationBarComponent} from "../../blocks/navigation-bar/navigation-bar.component";

@Component({
  selector: 'app-dashboard',
  imports: [
    NavigationBarComponent
  ],
  templateUrl: './dashboard.component.html',
  standalone: true,
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

}
