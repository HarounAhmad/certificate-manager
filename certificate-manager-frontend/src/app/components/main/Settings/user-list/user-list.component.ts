import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TableModule} from "primeng/table";
import {ButtonDirective, ButtonIcon} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {MultiSelect} from "primeng/multiselect";
import {FormsModule} from "@angular/forms";
import {InputText} from "primeng/inputtext";
import {NgIf} from "@angular/common";
import {UserService} from "../../../../service/user.service";
import {UserResponse} from "../../../../model/user-response";

@Component({
  selector: 'app-user-list',
  imports: [
    TableModule,
    ButtonDirective,
    ButtonIcon,
    Ripple,
    MultiSelect,
    FormsModule,
    InputText,
    NgIf
  ],
  templateUrl: './user-list.component.html',
  standalone: true,
  styleUrl: './user-list.component.scss'
})
export class UserListComponent {

  @Input() userList: any[] = [];
  @Input() loading: boolean = false;
  @Input() userRolesOptions: any[] = [];


  @Output() updateUser: EventEmitter<any> = new EventEmitter<any>();

  selectedUser: UserResponse | null = null;
  selectedUserRoles: any[] = [];

  deleteUser(user: any) {
    // Emit an event to delete the user
    this.updateUser.emit({ action: 'delete', user });
  }

  onRowEditSave(user: any) {
    if (this.selectedUserRoles.length > 0) {
      user.roles = this.selectedUserRoles.map(role => role.value);
    }
    this.updateUser.emit({ action: 'update', user });
    this.selectedUser = null; // Clear selection after saving
  }

  onRowEditCancel(user: any, ri: any) {
    this.selectedUser = null; // Clear selection after canceling
  }

  onRowEditInit(user: any) {
    this.selectedUser = {...user}; // Create a copy of the user for editing
    console.log(user.roles)
    this.selectedUserRoles = user.roles.map((role: any) => ({ name: role, value: role }))

  }

}
