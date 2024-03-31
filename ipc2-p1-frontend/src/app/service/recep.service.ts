import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Destination } from '../model/destination';
import { Customer } from '../model/customer';
import { Package } from '../model/package';
import { Parameter } from '../model/parameter';
import { BehaviorSubject, Observable, Subject, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecepService {

  url: string = 'http://localhost:8080/ipc2-p1-backend/recep';

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

  getDestinations() {
    return this.http.get<Destination[]>(`${this.url}/readAllDestinations`);
  }

  getCustomerByNit(nit : string) {
    return this.http.get<Customer>(`${this.url}/getCustomerByNit/${nit}`);
  }

  getInvoiceNo(){
    return this.http.get<Package>(`${this.url}/getInvoiceNo`);
  }

  getParameters(){
    return this.http.get<Parameter>(`${this.url}/getParameters`);
  }

  getAllCustomers(){
    return this.http.get<Customer[]>(`${this.url}/getAllCustomers`);
  }

  getAllPackagesOnStandby(){
    return this.http.get<Package[]>(`${this.url}/getAllPackagesOnStandby`);
  }

  filterPackagesOnStandby(filter : string){
    return this.http.get<Package[]>(`${this.url}/filterPackagesOnStandby/${filter}`);
  }

  createCustomer(customer : Customer){
    return this.http.post<Customer>(`${this.url}/createCustomer`, customer);
  }

  createPackage(packageS : Package){
    return this.http.post<Package>(`${this.url}/createPackage`, packageS);
  }

  updatePackage(packageS : Package){
    return this.http.put<Package>(`${this.url}/updatePackage`, packageS);
  }

  calculateDestinationFee(destination: Destination, packageS : Package, parameter : Parameter){
    return (packageS.weight * parameter.pricePerPound) + destination.destinationFee;
  }

  isNumber(str: string): boolean {
    return /^\d+$/.test(str);
  }

}
