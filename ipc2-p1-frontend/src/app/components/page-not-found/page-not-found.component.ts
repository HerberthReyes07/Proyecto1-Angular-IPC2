import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit{

  //@Output() toggleNavBarVisibility = new EventEmitter<void>();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    //this.toggleNavBarVisibility.emit();
  }
}
