import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ControlPoint } from 'src/app/model/control-point';
import { Destination } from 'src/app/model/destination';
import { Package } from 'src/app/model/package';
import { Route } from 'src/app/model/route';
import { User } from 'src/app/model/user';
import { OperService } from 'src/app/service/oper.service';
import { RecepService } from 'src/app/service/recep.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-process-packages',
  templateUrl: './process-packages.component.html',
  styleUrls: ['./process-packages.component.css']
})
export class ProcessPackagesComponent implements OnInit {

  packagesToProcess!: Package[];
  packageToProcess!: Package;
  filteredPackages!: Package[];
  controlPoints!: ControlPoint[];
  destinations!: Destination[];
  routes!: Route[];

  canProcess = true;
  isProcessFormOpened = false;

  searchForm = new FormGroup({
    searchByControl: new FormControl('0', Validators.required),
  });

  ngOnInit(): void {
    let operator: User = this.userService.getLocalStorageItem();

    this.recepService.getAllRoutes().subscribe(dataRoutes => {
      if (dataRoutes) {
        this.routes = dataRoutes;
        this.operService.getControlPointsByOperatorId(operator.id).subscribe(dataControlPoints => {
          if (dataControlPoints) {
            this.controlPoints = dataControlPoints;
            this.recepService.getAllDestinations().subscribe(dataDestionations => {
              if (dataDestionations) {
                this.destinations = dataDestionations;
                this.operService.getPackagesToProcessByOperatorId(operator.id).subscribe(dataPackages => {
                  if (dataPackages) {
                    this.packagesToProcess = dataPackages;
                    this.filteredPackages = dataPackages;
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  constructor(private operService: OperService, private recepService: RecepService, private userService: UserService) {
    this.filteredPackages = new Array();
    this.packagesToProcess = new Array();
    this.controlPoints = new Array();
    this.destinations = new Array();
    this.routes = new Array();
  }

  search() {

    if (this.searchByControl.value === "0") {
      this.filteredPackages = this.packagesToProcess;
      if (this.packagesToProcess.length != 0) {
        this.canProcess = true;
      } else {
        this.canProcess = false;
      }
    } else {
      this.operService.getUnprocessedPackagesByControlPointId(this.searchByControl.value).subscribe(dataPackages => {
        if (dataPackages) {
          this.filteredPackages = dataPackages;

          if (dataPackages.length != 0) {
            this.canProcess = true;
          } else {
            this.canProcess = false;
          }
        }
      });
    }
  }

  processPackage() {
    if (this.packagesToProcess.length != 0) {
      this.packageToProcess = this.filteredPackages[0];
      this.toggleProcessFormVisibility();
    }

  }

  processDone() {
    this.packagesToProcess = this.packagesToProcess.filter((item) => item.id !== this.packageToProcess.id);
    this.filteredPackages = this.filteredPackages.filter((item) => item !== this.packageToProcess);
  }

  toggleProcessFormVisibility() {
    this.isProcessFormOpened = !this.isProcessFormOpened;
  }

  getDestinationById(destinationId: number) {
    for (let index = 0; index < this.destinations.length; index++) {
      if (this.destinations[index].id === destinationId) {
        return this.destinations[index];
      }
    }
    return this.destinations[0];
  }

  getRouteById(routeId: number) {
    for (let index = 0; index < this.routes.length; index++) {
      if (this.routes[index].id === routeId) {
        return this.routes[index];
      }
    }
    return this.routes[0];
  }

  get searchByControl(): FormControl {
    return this.searchForm.get('searchByControl') as FormControl;
  }
}
