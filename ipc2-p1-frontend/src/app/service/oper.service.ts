import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Package } from '../model/package';
import { ControlPoint } from '../model/control-point';
import { ProcessDetail } from '../model/process-detail';

@Injectable({
  providedIn: 'root'
})
export class OperService {

  url: string = 'http://localhost:8080/ipc2-p1-backend';

  constructor(private http: HttpClient) { }

  getPackagesToProcessByOperatorId(operatorId: number){
    return this.http.get<Package[]>(`${this.url}/packages/to-process/operator/${operatorId}`);
  }

  getControlPointsByOperatorId(operatorId: number){
    return this.http.get<ControlPoint[]>(`${this.url}/control-points/operator/${operatorId}`);
  }

  getUnprocessedPackagesByControlPointId(controlPointId: number){
    return this.http.get<Package[]>(`${this.url}/packages/to-process/control-point/${controlPointId}`);
  }

  processPackage(packageId: number, processDetail: ProcessDetail){
    return this.http.post<ProcessDetail>(`${this.url}/packages/process/${packageId}`, processDetail);
  }
}
