import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TypeUser } from 'src/app/model/type-user';
import { User } from 'src/app/model/user';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {
  
  @Input() userToUpdate!: User;
  @Input() isNewUser!: boolean;
  @Output() toggleFormVisibility = new EventEmitter<void>();
  @Output() actionCompleted = new EventEmitter<void>();

  userToSend!: User;
  isUserCreated = false;
  isUserUpdated = false;
  hasError = false;
  message = '';

  userForm = new FormGroup({
    name: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    dpi: new FormControl('', [Validators.required, Validators.minLength(13), Validators.maxLength(13)]),
    sex: new FormControl('0', Validators.required),
    typeUser: new FormControl('0', Validators.required),
  });

  constructor(private adminService: AdminService, private recepService: RecepService) { }

  ngOnInit(): void {
    if (!this.isNewUser) {
      this.nameControl.setValue(this.userToUpdate.name);
      this.usernameControl.setValue(this.userToUpdate.username);
      this.passwordControl.setValue(this.userToUpdate.password);
      this.dpiControl.setValue(this.userToUpdate.dpi);
      this.sexControl.setValue(this.userToUpdate.sex);
      this.typeUserControl.setValue(this.getCodeTypeUser(this.userToUpdate.typeUser));
    }
  }

  sendUser() {
    if (this.validateFields()) {
      if (this.recepService.isNumber(this.dpiControl.value)) {
        this.userToSend = new User();
        this.userToSend.name = this.nameControl.value;
        this.userToSend.username = this.usernameControl.value;
        this.userToSend.password = this.passwordControl.value;
        this.userToSend.dpi = this.dpiControl.value;
        this.userToSend.sex = this.sexControl.value;
        this.userToSend.typeUser = this.getTypeUserByCode(this.typeUserControl.value);

        if (this.isNewUser) {
          this.adminService.createUser(this.userToSend).subscribe(dataUser => {
            if (dataUser) {
              this.message = "Usuario creado exitosamente!";
              this.isUserCreated = true;
              this.clearForm();
              this.actionCompleted.emit();
            }
          });
        } else {
          this.userToSend.id = this.userToUpdate.id;
          this.userToSend.active = this.userToUpdate.active;
          this.adminService.updateUser(this.userToSend).subscribe(dataUser => {
            if (dataUser) {
              this.message = "Usuario editado exitosamente!";
              this.isUserUpdated = true;
              this.actionCompleted.emit();
            }
          });
        }
      } else {
        this.message = "La información ingresada no es válida"
        this.hasError = true;
      }
    } else {
      this.message = "Por favor complete todos los campos"
      this.hasError = true;
    }
  }

  get nameControl(): FormControl {
    return this.userForm.get('name') as FormControl;
  }

  get usernameControl(): FormControl {
    return this.userForm.get('username') as FormControl;
  }

  get passwordControl(): FormControl {
    return this.userForm.get('password') as FormControl;
  }

  get dpiControl(): FormControl {
    return this.userForm.get('dpi') as FormControl;
  }

  get sexControl(): FormControl {
    return this.userForm.get('sex') as FormControl;
  }

  get typeUserControl(): FormControl {
    return this.userForm.get('typeUser') as FormControl;
  }

  getTypeUserByCode(code: string) {
    if (code === "1") {
      return TypeUser.ADMINISTRATOR;
    } else if (code === "2") {
      return TypeUser.OPERATOR;
    }
    return TypeUser.RECEPTIONIST;
  }

  getCodeTypeUser(typeUser: TypeUser) {
    if (typeUser === TypeUser.ADMINISTRATOR) {
      return "1";
    } else if (typeUser === TypeUser.OPERATOR) {
      return "2";
    }
    return "3"
  }

  validateFields() {
    if (this.nameControl.value === '' || this.usernameControl.value === '' || this.passwordControl.value === ''
      || this.dpiControl.value === '' || this.sexControl.value === '0' || this.typeUserControl.value === '0') {
      return false;
    }
    return true;
  }

  clearForm() {
    this.userForm.reset();
    this.sexControl.setValue('0');
    this.typeUserControl.setValue('0');
  }

  toggleHasError() {
    this.hasError = !this.hasError;
  }

  closeForm() {
    this.toggleFormVisibility.emit();
  }

  toggleactionCompleted() {
    this.isUserCreated = false;
  }
}
