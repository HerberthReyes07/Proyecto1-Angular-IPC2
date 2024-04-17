import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RoutesReport } from '../model/routes-report';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  url: string = 'http://localhost:8080/ipc2-p1-backend';

  constructor(private http: HttpClient) { }

  getRoutesReports(){
    return this.http.get<RoutesReport[]>(`${this.url}/routes/report`);
  }
}
