import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ControlPoint } from 'src/app/model/control-point';
import { Customer } from 'src/app/model/customer';
import { Destination } from 'src/app/model/destination';
import { LocationReport } from 'src/app/model/location-report';
import { Package } from 'src/app/model/package';
import { Route } from 'src/app/model/route';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-check-location',
  templateUrl: './check-location.component.html',
  styleUrls: ['./check-location.component.css']
})
export class CheckLocationComponent implements OnInit {

  packagesOnRoute!: Package[];
  locations!: LocationReport[];
  destinations!: Destination[];
  customers !: Customer[];
  controlPoints !: ControlPoint[];
  routes !: Route[];
  filteredLocations!: LocationReport[];

  searchForm = new FormGroup({
    searchByControl: new FormControl('', Validators.required),
  });

  constructor(private recepService: RecepService) {
    this.packagesOnRoute = new Array();
    this.filteredLocations = new Array();
    this.locations = new Array();
    this.destinations = new Array();
    this.customers = new Array();
    this.controlPoints = new Array();
    this.routes = new Array();
  }

  ngOnInit(): void {
    this.recepService.getDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.recepService.getAllCustomers().subscribe(dataCustomers => {
          if (dataCustomers) {
            this.customers = dataCustomers;
            this.recepService.getAllPackagesOnRoute().subscribe(dataPackages => {
              if (dataPackages) {
                this.packagesOnRoute = dataPackages;
                this.recepService.getAllControlPoints().subscribe(dataControlPoints => {
                  if (dataControlPoints) {
                    this.controlPoints = dataControlPoints;
                    this.recepService.getAllRoutes().subscribe(dataRoutes => {
                      if (dataRoutes) {
                        this.routes = dataRoutes;
                        this.recepService.getAllPackagesLocations().subscribe(dataLocations => {
                          if (dataLocations) {
                            this.locations = dataLocations;
                            this.filteredLocations = dataLocations;
                          }
                        });
                      }
                    });
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  getPackageById(packageId: number) {
    for (let index = 0; index < this.packagesOnRoute.length; index++) {
      if (this.packagesOnRoute[index].id === packageId) {
        return this.packagesOnRoute[index];
      }
    }
    return this.packagesOnRoute[0];
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

  getControlPointById(controlPointId: number){
    for (let index = 0; index < this.controlPoints.length; index++) {
      if (this.controlPoints[index].id === controlPointId) {
        return this.controlPoints[index];
      }
    }
    return this.controlPoints[0];
  }

  getRouteById(routeId: number){
    for (let index = 0; index < this.routes.length; index++) {
      if (this.routes[index].id === routeId) {
        return this.routes[index];
      }     
    }
    return this.routes[0];
  }

  search() {
    this.recepService.filterLocations(this.searchByControl.value).subscribe(data => {
      if (data) {
        this.filteredLocations = data;
      }
    }, error => {
      //this.filteredPackages = this.packagesOnStandby;
    });
  }

  get searchByControl(): FormControl {
    return this.searchForm.get('searchByControl') as FormControl;
  }
}
