import { Component } from '@angular/core';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-home-recep',
  templateUrl: './home-recep.component.html',
  styleUrls: ['./home-recep.component.css']
})
export class HomeRecepComponent {
  constructor(private userService: UserService) { }

  getName() {
    let user: User = this.userService.getLocalStorageItem();
    return user ? user.name : '';
  }
}
