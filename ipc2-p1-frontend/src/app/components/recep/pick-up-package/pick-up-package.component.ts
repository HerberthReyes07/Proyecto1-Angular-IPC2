import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Customer } from 'src/app/model/customer';
import { Destination } from 'src/app/model/destination';
import { Package } from 'src/app/model/package';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-pick-up-package',
  templateUrl: './pick-up-package.component.html',
  styleUrls: ['./pick-up-package.component.css']
})
export class PickUpPackageComponent implements OnInit {

  packagesOnStandby!: Package[];
  destinations!: Destination[];
  customers !: Customer[];
  filteredPackages!: Package[];

  pickedUpPackage = false;

  searchForm = new FormGroup({
    searchByControl: new FormControl(''),
  });

  constructor(private recepService: RecepService) {
    this.filteredPackages = new Array();
    this.packagesOnStandby = new Array();
    this.destinations = new Array();
    this.customers = new Array();
  }

  ngOnInit(): void {
    this.recepService.getAllDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.recepService.getAllCustomers().subscribe(dataCustomers => {
          if (dataCustomers) {
            this.customers = dataCustomers;
            this.recepService.getAllPackagesOnStandby().subscribe(dataPackagesOnStandby => {
              if (dataPackagesOnStandby) {
                this.packagesOnStandby = dataPackagesOnStandby;
                this.filteredPackages = dataPackagesOnStandby;
              }
            });
          }
        });
      }
    });
  }

  getCustomerById(customerId: number) {
    for (let index = 0; index < this.customers.length; index++) {
      if (this.customers[index].id === customerId) {
        return this.customers[index];
      }
    }
    return this.customers[0];
  }

  getDestinationById(destinationId: number) {
    for (let index = 0; index < this.destinations.length; index++) {
      if (this.destinations[index].id === destinationId) {
        return this.destinations[index];
      }
    }
    return this.destinations[0];
  }
  
  pickUpPackage(packageToPickUp: Package) {
    this.recepService.updatePackage(packageToPickUp).subscribe(data => {
      if (data) {
        console.log("paquete actualizado: " + data.status);
        this.pickedUpPackage = true;
        this.packagesOnStandby = this.packagesOnStandby.filter((item) => item !== packageToPickUp);
        //this.filteredPackages = this.packagesOnStandby; 
        this.search();
      }
    });
  }
  
  search() {

    if (this.searchByControl.value === "") {
      this.filteredPackages = this.packagesOnStandby;
    } else {
      this.recepService.filterPackagesOnStandby(this.searchByControl.value).subscribe(data => {
        if (data) {
          this.filteredPackages = data;
        }
      }, error => {
        //this.filteredPackages = this.packagesOnStandby;
      });
    }

    
  }

  get searchByControl(): FormControl {
    return this.searchForm.get('searchByControl') as FormControl;
  }

  changePickedUpPackageValue(){
    this.pickedUpPackage = false;
  }
}
