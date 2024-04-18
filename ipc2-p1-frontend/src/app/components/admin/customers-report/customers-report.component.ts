import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Customer } from 'src/app/model/customer';
import { CustomersReport } from 'src/app/model/customers-report';
import { Destination } from 'src/app/model/destination';
import { Package } from 'src/app/model/package';
import { PackageStatus } from 'src/app/model/package-status';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-customers-report',
  templateUrl: './customers-report.component.html',
  styleUrls: ['./customers-report.component.css']
})
export class CustomersReportComponent implements OnInit {

  destinations!: Destination[];
  customers!: Customer[];
  packages!: Package[];
  customersReports!: CustomersReport[];
  customersReportsFiltered!: CustomersReport[];

  searchForm = new FormGroup({
    searchByControl: new FormControl(''),
  });

  ngOnInit(): void {

    this.recepService.getDestinations().subscribe(dataDestination => {
      if (dataDestination) {
        this.destinations = dataDestination;
        this.recepService.getAllCustomers().subscribe(dataCustomers => {
          if (dataCustomers) {
            this.customers = dataCustomers;
            this.adminService.getAllPackages().subscribe(dataPackages => {
              if (dataPackages) {
                this.packages = dataPackages;
                this.adminService.getCustomersReport().subscribe(dataCustomersReport => {
                  if (dataCustomersReport) {
                    this.customersReports = dataCustomersReport;
                    this.customersReportsFiltered = dataCustomersReport;
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  search(){
    if (this.searchByControl.value === "") {
      this.customersReportsFiltered = this.customersReports;
    } else {
      this.adminService.getCustomersReportByFilter(this.searchByControl.value).subscribe(dataCustomersReport => {
        if (dataCustomersReport) {
          this.customersReportsFiltered = dataCustomersReport;
        }
      });
    }
  }

  get searchByControl(): FormControl {
    return this.searchForm.get('searchByControl') as FormControl;
  }

  getDestinationById(destinationId: number) {
    for (let index = 0; index < this.destinations.length; index++) {
      if (this.destinations[index].id === destinationId) {
        return this.destinations[index];
      }
    }
    return this.destinations[0];
  }

  getCustomerById(customerId: number){
    for (let index = 0; index < this.customers.length; index++) {
      if (this.customers[index].id === customerId) {
        return this.customers[index];
      }
    }
    return this.customers[0];
  }

  getPackageById(packageId: number){
    for (let index = 0; index < this.packages.length; index++) {
      if (this.packages[index].id === packageId) {
        return this.packages[index];
      }
    }
    return this.packages[0];
  }

  getStatus(status: PackageStatus){
    if (status == PackageStatus.EN_BODEGA) {
      return "En Bodega"
    } else if (status == PackageStatus.EN_PUNTO_CONTROL){
      return "En Punto de Control"
    } else if (status == PackageStatus.EN_ESPERA_RETIRO){
      return "En Espera de Retiro"
    }
    return "Retirado"
  }

  constructor(private adminService: AdminService, private recepService: RecepService) { }
}
