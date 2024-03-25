import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root',
})

export class IsLoggedInGuard implements CanActivate {

  constructor(private userService: UserService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<true | UrlTree> {

    let user: User = this.userService.getLocalStorageItem();

    if (user) {
      this.userService.logIn$();
      /*       this.userService.loggedIn$.subscribe(
              (data) => {
                console.log("loggedIn? " + data); // Aquí obtienes el valor emitido por el Observable
              }
            ); */
    }
    return this.userService.loggedIn$.pipe(
      map((loggedIn) => loggedIn || this.router.parseUrl('/login'))
    );
  }
}
