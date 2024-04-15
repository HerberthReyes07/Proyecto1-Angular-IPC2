import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { TypeUser } from '../model/type-user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string = 'http://localhost:8080/ipc2-p1-backend';
  private loggedIn = new BehaviorSubject<boolean>(false);
  loggedIn$ = this.loggedIn.asObservable();

  constructor(private http: HttpClient, private router: Router) { }

  logIn(user: User) {
    return this.http.post<User>(`${this.url}/login`, user);
  }

  logIn$(): void {
    this.loggedIn.next(true);
  }

  logOut$(): void {
    localStorage.removeItem('currentUser');
    this.loggedIn.next(false);
  }

  getLocalStorageItem(): User {
    let stringUser = localStorage.getItem('currentUser');
    let user: User = stringUser ? JSON.parse(stringUser) : null;
    return user;
  }

  setLocalStorageItem(user: User): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  getTypeUser(verifyUser: string): TypeUser {
    switch (verifyUser) {
      case "recep":
        return TypeUser.RECEPTIONIST;
      case "oper":
        return TypeUser.OPERATOR;
      default:
        return TypeUser.ADMINISTRATOR;
    }
  }
}
