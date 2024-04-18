import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RoutesReport } from '../model/routes-report';
import { EarningsReport } from '../model/earnings-report';
import { Package } from '../model/package';
import { CustomersReport } from '../model/customers-report';
import { PopularRoutesReport } from '../model/popular-routes-report';

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
}
