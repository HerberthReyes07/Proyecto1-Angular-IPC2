import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  constructor(private userService: UserService, private router: Router) { }

  userLoggedIn() {
    return localStorage.getItem('currentUser') ? true : false;
  }

  getUserType() {
    let user: User = this.userService.getLocalStorageItem();
    return user.typeUser;
  }

  getUserName() {
    let user: User = this.userService.getLocalStorageItem();
    return user ? user.username : '';
  }

  logOut() {
    this.userService.logOut$();
    this.router.navigate(['/homepage']);
  }
}
