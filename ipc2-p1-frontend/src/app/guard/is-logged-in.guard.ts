import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { TypeUser } from '../model/type-user';

@Injectable({
  providedIn: 'root',
})

export class IsLoggedInGuard implements CanActivate {

  routeToVerify!: string;

  constructor(private userService: UserService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<true | UrlTree> {

    let user: User = this.userService.getLocalStorageItem();

    if (user) {
      this.userService.logIn$();
      if (route.routeConfig?.path) {
        this.routeToVerify = route.routeConfig?.path;
        let split: string[] = this.routeToVerify.split('/');
        if (this.userService.getTypeUser(split[0]) != user.typeUser) {
          return of(this.router.parseUrl('**'));
        }
      }
    }

    return this.userService.loggedIn$.pipe(
      map((loggedIn) => loggedIn || this.router.parseUrl('/login'))
    );
  }

  
}
