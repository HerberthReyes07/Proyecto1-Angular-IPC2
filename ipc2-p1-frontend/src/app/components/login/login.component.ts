import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  user !: User;
  hasError = false;

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  get usernameControl(): FormControl {
    return this.loginForm.get('username') as FormControl;
  }

  get passwordControl(): FormControl {
    return this.loginForm.get('password') as FormControl;
  }

  constructor(private userService: UserService, private router: Router) {
    this.user = new User();
  }

  signIn() {
    this.user.username = this.usernameControl.value;
    this.user.password = this.passwordControl.value;

    this.userService.logIn(this.user)
      .subscribe(consultedUser => {
        if (consultedUser) {
          this.userService.logIn$();
          this.userService.setLocalStorageItem(consultedUser);
          this.router.navigate(['/homepage']);
        }
      }, error => {
        console.log('Código de estado:', error.status);
        this.hasError = !this.hasError;
      });
  }
}
