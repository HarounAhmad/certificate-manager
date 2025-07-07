import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../service/user.service";
import {Panel} from "primeng/panel";
import {UserListComponent} from "../Settings/user-list/user-list.component";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-settings',
  imports: [
    Panel,
    UserListComponent
  ],
  templateUrl: './settings.component.html',
  standalone: true,
  styleUrl: './settings.component.scss'
})
export class SettingsComponent implements OnInit{

  userList: any[] = [];
  userRolesOptions: any[] = [];
  loadingUserList: boolean = true;


  constructor(
    private userService: UserService,
  ) {
  }

  ngOnInit() {
    this.reload();
  }


  reload(){
    this.loadingUserList = true;
    forkJoin(
      [
        this.userService.getAllUsers(),
        this.userService.getUserRoleOptions()
      ]
    ).subscribe(([users, roles]) => {
        this.userList = []
        this.userRolesOptions = []
        this.userList = users;
        this.userRolesOptions = roles.map(role => ({ name: role, value: role }));
        this.loadingUserList = false;
      }
    )
  }


  updateUser($event: any) {
    console.log($event)
    if ($event.action === 'delete') {
      this.userService.deleteUser($event.user.id).subscribe(() => {
        this.reload();
      });
    } else if ($event.action === 'update') {
      this.userService.updateUser($event.user).subscribe(() => {
        this.reload();
      });
    }
  }
}
