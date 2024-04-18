import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Destination } from 'src/app/model/destination';
import { PopularRoutesReport } from 'src/app/model/popular-routes-report';
import { Route } from 'src/app/model/route';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-popular-routes-report',
  templateUrl: './popular-routes-report.component.html',
  styleUrls: ['./popular-routes-report.component.css']
})
export class PopularRoutesReportComponent implements OnInit{

  destinations !: Destination[];
  routes!: Route[];
  popularRoutesReports!: PopularRoutesReport[];
  popularRoutesReportsFiltered!: PopularRoutesReport[];
  hasError = false;

  filterForm = new FormGroup({
    initialDateControl: new FormControl(''),
    finalDateControl: new FormControl(''),
  });

  ngOnInit(): void {
    this.recepService.getDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.recepService.getAllRoutes().subscribe(dataRoutes => {
          if (dataRoutes) {
            this.routes = dataRoutes;
            this.adminService.getAllPopularRoutes().subscribe(dataPopularRoutes => {
              if (dataPopularRoutes) {
                this.popularRoutesReports = dataPopularRoutes;
                this.popularRoutesReportsFiltered = dataPopularRoutes;
              }
            });
          }
        });
      }
    });
  }

  constructor(private adminService: AdminService, private recepService: RecepService) {
    this.routes = new Array();
  }

  filter(){
    if (this.initialDateControl.value == "" && this.finalDateControl.value == "") {
      this.popularRoutesReportsFiltered = this.popularRoutesReports;
    } else if ((this.initialDateControl.value == "" && this.finalDateControl.value != "") || (this.initialDateControl.value != "" && this.finalDateControl.value == "")) {
      this.hasError = true;
    } else {
      this.adminService.getPopularRoutesByDateRange(this.initialDateControl.value, this.finalDateControl.value).subscribe(dataPopularRoutes => {
        if (dataPopularRoutes) {
          this.popularRoutesReportsFiltered = dataPopularRoutes;
        }
      });
    }
  }

  clear(){
    this.initialDateControl.setValue('');
    this.finalDateControl.setValue('');
    this.filter();
  }

  get initialDateControl(): FormControl {
    return this.filterForm.get('initialDateControl') as FormControl;
  }

  get finalDateControl(): FormControl {
    return this.filterForm.get('finalDateControl') as FormControl;
  }

  getRouteById(routeId: number) {
    for (let index = 0; index < this.routes.length; index++) {
      if (this.routes[index].id === routeId) {
        return this.routes[index];
      }
    }
    return this.routes[0];
  }

  getDestinationById(destinationId: number) {
    for (let index = 0; index < this.destinations.length; index++) {
      if (this.destinations[index].id === destinationId) {
        return this.destinations[index];
      }
    }
    return this.destinations[0];
  }

  getState(state: boolean){
    if (state) {
      return "Activa"
    } else {
      return "Inactiva"
    }
  }

  toggleHasError(){
    this.hasError = false;
  }

}
