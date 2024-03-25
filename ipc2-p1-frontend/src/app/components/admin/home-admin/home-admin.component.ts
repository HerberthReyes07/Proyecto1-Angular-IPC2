import { Component } from '@angular/core';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent {

  constructor(private userService: UserService) { }

  getName() {
    let user: User = this.userService.getLocalStorageItem();
    return user ? user.name : '';
  }
}
