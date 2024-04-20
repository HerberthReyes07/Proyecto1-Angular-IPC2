import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Destination } from '../model/destination';
import { Customer } from '../model/customer';
import { Package } from '../model/package';
import { Parameter } from '../model/parameter';
import { BehaviorSubject, Observable, Subject, map } from 'rxjs';
import { LocationReport } from '../model/location-report';
import { ControlPoint } from '../model/control-point';
import { Route } from '../model/route';

@Injectable({
  providedIn: 'root'
})
export class RecepService {

  url: string = 'http://localhost:8080/ipc2-p1-backend';

  private packages = new BehaviorSubject<Package[]>([]);
  //private packagesOnStandby = new BehaviorSubject<Package[]>([]);

  packages$ = this.packages.asObservable();

  constructor(private http: HttpClient) { }

  total$: Observable<number> = this.packages$.pipe(
    map((packages) => packages.reduce((acc, { shippingCost }) => (acc += shippingCost), 0))
  );

  addPackage(packageToAdd: Package): void {
    this.packages.next([...this.packages.value, packageToAdd]);
  }

  deletePackage(packageToDelete: Package): void {
    this.packages.next(this.packages.value.filter((item) => item !== packageToDelete));
  }

  cleanPackages(){
    this.packages.next([]);
  }

  getAllDestinations() {
    return this.http.get<Destination[]>(`${this.url}/destinations`);
  }

  getValidDestinations() {
    return this.http.get<Destination[]>(`${this.url}/destinations/valid`);
  }

  getCustomerByNit(nit : string) {
    return this.http.get<Customer>(`${this.url}/customers/${nit}`);
  }

  getInvoiceNo(){
    return this.http.get<Package>(`${this.url}/packages/invoiceNo`);
  }

  getCurrentParameter(){
    return this.http.get<Parameter>(`${this.url}/parameters/current`);
  }

  getAllCustomers(){
    return this.http.get<Customer[]>(`${this.url}/customers`);
  }

  getAllPackagesOnStandby(){
    return this.http.get<Package[]>(`${this.url}/packages/on-standby`);
  }

  getAllPackagesOnRoute(){
    return this.http.get<Package[]>(`${this.url}/packages/on-route`);
  }

  getAllPackagesLocations(){
    return this.http.get<LocationReport[]>(`${this.url}/packages/locations`);
  }

  getAllControlPoints(){
    return this.http.get<ControlPoint[]>(`${this.url}/control-points`);
  }

  getAllRoutes(){
    return this.http.get<Route[]>(`${this.url}/routes`);
  }

  filterPackagesOnStandby(filter : string){
    return this.http.get<Package[]>(`${this.url}/packages/on-standby/${filter}`);
  }

  filterLocations(filter : string){
    return this.http.get<LocationReport[]>(`${this.url}/packages/location/${filter}`);
  }

  createCustomer(customer : Customer){
    return this.http.post<Customer>(`${this.url}/customers`, customer);
  }

  createPackage(packageS : Package){
    return this.http.post<Package>(`${this.url}/packages`, packageS);
  }

  updatePackage(packageS : Package){
    return this.http.put<Package>(`${this.url}/packages/${packageS.id}`, packageS);
  }

  calculateDestinationFee(destination: Destination, packageS : Package, parameter : Parameter){
    return (packageS.weight * parameter.pricePerPound) + destination.destinationFee;
  }

  isNumber(str: string): boolean {
    return /^\d+$/.test(str);
  }

  isDecimal(str: string): boolean {
    return /^\d+\.?\d+$/.test(str);
  }

}
