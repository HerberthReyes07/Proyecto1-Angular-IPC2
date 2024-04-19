import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { TypeUser } from 'src/app/model/type-user';
import { User } from 'src/app/model/user';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  users!: User[];
  usersFiltered!: User[];
  isUserFormOpened = false;
  userToUpdate!: User;
  isNewUser = true;
  message = "";
  canProceed = false;

  filterForm = new FormGroup({
    typeUserControl: new FormControl('0'),
    sexControl: new FormControl('0'),
    stateControl: new FormControl('0'),
  });

  ngOnInit(): void {
    this.adminService.getAllUsers().subscribe(dataUsers => {
      if (dataUsers) {
        this.users = dataUsers;
        this.usersFiltered = dataUsers;
      }
    });
  }

  constructor(private adminService: AdminService) { }

  newUser() {
    this.isNewUser = true;
    this.toggleUserFormVisibility();
  }

  filter() {
    if (this.typeUserControl.value === "0") {
      if (this.stateControl.value === "0") {
        if (this.sexControl.value === "0") {
          this.usersFiltered = this.users;
        } else {
          this.usersFiltered = this.users.filter((item) => item.sex == this.sexControl.value);
        }
      } else {
        if (this.sexControl.value === "0") {
          this.usersFiltered = this.users.filter((item) => item.active == this.getStateByCode(this.stateControl.value));
        } else {
          this.usersFiltered = this.users.filter((item) => (item.active == this.getStateByCode(this.stateControl.value) && item.sex == this.sexControl.value));
        }
      }
    } else {
      if (this.stateControl.value == "0") {
        if (this.sexControl.value === "0") {
          this.usersFiltered = this.users.filter((item) => item.typeUser == this.getTypeUserByCode(this.typeUserControl.value));
        } else {
          this.usersFiltered = this.users.filter((item) => (item.typeUser == this.getTypeUserByCode(this.typeUserControl.value) && item.sex == this.sexControl.value));
        }
      } else {
        if (this.sexControl.value === "0") {
          this.usersFiltered = this.users.filter((item) => (item.typeUser == this.getTypeUserByCode(this.typeUserControl.value) && item.active == this.getStateByCode(this.stateControl.value)));
        } else {
          this.usersFiltered = this.users.filter((item) => (item.typeUser == this.getTypeUserByCode(this.typeUserControl.value) && item.active == this.getStateByCode(this.stateControl.value) && item.sex == this.sexControl.value));
        }
      }
    }
  }

  changeUserState(user: User) {
    if (user.active === false) {
      user.active = true;
    } else {
      user.active = false;
    }
    this.adminService.updateUser(user).subscribe(dataUser => {
      if (dataUser) {
        if (dataUser.active) {
          this.message = "El usuario: " + user.name + " ha sido activado";
        } else {
          this.message = "El usuario: " + user.name + " ha sido desactivado";
        }
        this.canProceed = true;
        this.filter();
      }
    });
  }

  updateUser(user: User){
    this.isNewUser = false;
    this.userToUpdate = user;
    this.toggleUserFormVisibility();
  }

  toggleUserFormVisibility() {
    this.isUserFormOpened = !this.isUserFormOpened;
  }

  actionCompleted(){
    this.adminService.getAllUsers().subscribe(dataUsers => {
      if (dataUsers) {
        this.users = dataUsers;
        this.filter();
      }
    });
  }

  get typeUserControl(): FormControl {
    return this.filterForm.get('typeUserControl') as FormControl;

  }

  get sexControl(): FormControl {
    return this.filterForm.get('sexControl') as FormControl;
  }

  get stateControl(): FormControl {
    return this.filterForm.get('stateControl') as FormControl;
  }

  getTypeUser(typeUser: TypeUser) {
    if (typeUser === TypeUser.ADMINISTRATOR) {
      return "Administrador";
    } else if (typeUser === TypeUser.OPERATOR) {
      return "Operador";
    }
    return "Recepcionista"
  }

  getTypeUserByCode(code: string) {
    if (code === "1") {
      return TypeUser.ADMINISTRATOR;
    } else if (code === "2") {
      return TypeUser.OPERATOR;
    }
    return TypeUser.RECEPTIONIST;
  }

  getState(state: boolean) {
    if (state) {
      return "Activo";
    }
    return "Inactivo";
  }

  getStateByCode(code: string) {
    if (code === "1") {
      return true;
    }
    return false;
  }

  toggleCanProceed(){
    this.canProceed = !this.canProceed;
  }
}
