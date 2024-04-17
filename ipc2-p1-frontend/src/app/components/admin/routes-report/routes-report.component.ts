import { Component, OnInit } from '@angular/core';
import { Destination } from 'src/app/model/destination';
import { Route } from 'src/app/model/route';
import { RoutesReport } from 'src/app/model/routes-report';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-routes-report',
  templateUrl: './routes-report.component.html',
  styleUrls: ['./routes-report.component.css']
})
export class RoutesReportComponent implements OnInit{

  destinations !: Destination[];
  routes!: Route[];
  routesReports!: RoutesReport[];
  routesReportsFiltered!: RoutesReport[];
  
  ngOnInit(): void {

    this.recepService.getDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.recepService.getAllRoutes().subscribe(dataRoutes => {
          if (dataRoutes) {
            this.routes = dataRoutes;
            this.adminService.getRoutesReports().subscribe(dataRoutesReports => {
              if (dataRoutesReports) {
                this.routesReports = dataRoutesReports;
                this.routesReportsFiltered = dataRoutesReports;
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
    const selectElement = document.getElementById('state') as HTMLSelectElement;

    if (selectElement.value === "0") {
      this.routesReportsFiltered = this.routesReports
    } else if (selectElement.value === "1"){
      this.routesReportsFiltered = this.routesReports.filter((item) => this.getRouteById(item.routeId).active !== false);
    } else if (selectElement.value === "2"){
      this.routesReportsFiltered = this.routesReports.filter((item) => this.getRouteById(item.routeId).active !== true);
    }
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
}
