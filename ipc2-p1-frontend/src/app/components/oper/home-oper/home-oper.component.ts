import { Component } from '@angular/core';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-home-oper',
  templateUrl: './home-oper.component.html',
  styleUrls: ['./home-oper.component.css']
})
export class HomeOperComponent {

  constructor(private userService: UserService) { }

  getName() {
    let user: User = this.userService.getLocalStorageItem();
    return user ? user.name : '';
  }
}
