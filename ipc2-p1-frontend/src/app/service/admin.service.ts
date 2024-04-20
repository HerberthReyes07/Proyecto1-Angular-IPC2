import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RoutesReport } from '../model/routes-report';
import { EarningsReport } from '../model/earnings-report';
import { Package } from '../model/package';
import { CustomersReport } from '../model/customers-report';
import { PopularRoutesReport } from '../model/popular-routes-report';
import { User } from '../model/user';
import { Route } from '../model/route';
import { ControlPoint } from '../model/control-point';
import { Destination } from '../model/destination';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  url: string = 'http://localhost:8080/ipc2-p1-backend';

  constructor(private http: HttpClient) { }

  getRoutesReports(){
    return this.http.get<RoutesReport[]>(`${this.url}/routes/report`);
  }

  getAllEarnings(){
    return this.http.get<EarningsReport[]>(`${this.url}/routes/earnings/all`);
  }

  getEarningsByDateRange(initialDate: string, finalDate: string){
    return this.http.get<EarningsReport[]>(`${this.url}/routes/earnings/${initialDate}/${finalDate}`);
  }

  getCustomersReport(){
    return this.http.get<CustomersReport[]>(`${this.url}/customers/reports/all`);
  }

  getCustomersReportByFilter(filter: string){
    return this.http.get<CustomersReport[]>(`${this.url}/customers/reports/${filter}`);
  }

  getAllPopularRoutes(){
    return this.http.get<PopularRoutesReport[]>(`${this.url}/routes/popular/all`);
  }

  getPopularRoutesByDateRange(initialDate: string, finalDate: string){
    return this.http.get<PopularRoutesReport[]>(`${this.url}/routes/popular/${initialDate}/${finalDate}`);
  }

  getAllPackages(){
    return this.http.get<Package[]>(`${this.url}/packages`);
  }

  getAllUsers(){
    return this.http.get<User[]>(`${this.url}/users`);
  }

  getAllOperatorsUsers(){
    return this.http.get<User[]>(`${this.url}/users/operators`);
  }

  getAllControlPoints(){
    return this.http.get<ControlPoint[]>(`${this.url}/control-points`);
  }

  getNextOrderNo(routeId: number){
    return this.http.get<ControlPoint>(`${this.url}/control-points/orderNo/route/${routeId}`);
  }
  
  createUser(user: User){
    return this.http.post<User>(`${this.url}/users`, user);
  }
  
  createDestination(destination: Destination){
    return this.http.post<Destination>(`${this.url}/destinations`, destination);
  }

  createRoute(route: Route){
    return this.http.post<Route>(`${this.url}/routes`, route);
  }

  createControlPoint(controlPoint: ControlPoint){
    return this.http.post<ControlPoint>(`${this.url}/control-points`, controlPoint);
  }
  
  updateUser(user: User){
    return this.http.put<User>(`${this.url}/users/${user.id}`, user);
  }

  updateDestination(destination: Destination){
    return this.http.put<Destination>(`${this.url}/destinations/${destination.id}`, destination);
  }

  updateRoute(route: Route){
    return this.http.put<Route>(`${this.url}/routes/${route.id}`, route);
  }

  updateControlPoint(controlPoint: ControlPoint){
    return this.http.put<ControlPoint>(`${this.url}/control-points/${controlPoint.id}`, controlPoint);
  }

  updateControlPoints(controlPoint: ControlPoint){
    return this.http.put<ControlPoint>(`${this.url}/control-points/orderNo/${controlPoint.routeId}`, controlPoint);
  }
}
