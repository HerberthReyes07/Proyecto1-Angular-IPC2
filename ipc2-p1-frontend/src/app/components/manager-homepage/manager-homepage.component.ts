import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TypeUser } from 'src/app/model/type-user';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-manager-homepage',
  template: ``,
  styles: [``]
})
export class ManagerHomepageComponent {
  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {

    let user: User = this.userService.getLocalStorageItem();

    if (user) {
      if(user.typeUser == TypeUser.ADMINISTRATOR){
        this.router.navigate(['/admin/home']);
      } else if(user.typeUser == TypeUser.OPERATOR){
        this.router.navigate(['/oper/home']);
      } else {
        this.router.navigate(['/recep/home']);
      }      
    }
    else {
      this.router.navigate(['/login']);
    }
  }
}
