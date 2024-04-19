import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Destination } from 'src/app/model/destination';
import { EarningsReport } from 'src/app/model/earnings-report';
import { Route } from 'src/app/model/route';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-earnings-report',
  templateUrl: './earnings-report.component.html',
  styleUrls: ['./earnings-report.component.css']
})
export class EarningsReportComponent implements OnInit{

  destinations !: Destination[];
  routes!: Route[];
  earningnsReports!: EarningsReport[];
  earningnsReportsFiltered!: EarningsReport[];
  hasError = false;

  filterForm = new FormGroup({
    initialDateControl: new FormControl(''),
    finalDateControl: new FormControl(''),
    routeControl: new FormControl('0'),
  });

  ngOnInit(): void {
    this.recepService.getAllDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.recepService.getAllRoutes().subscribe(dataRoutes => {
          if (dataRoutes) {
            this.routes = dataRoutes;
            this.adminService.getAllEarnings().subscribe(dataEarningsReports => {
              if (dataEarningsReports) {
                this.earningnsReports = dataEarningsReports;
                this.earningnsReportsFiltered = dataEarningsReports;
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
      if (this.routeControl.value == "0") {
        this.earningnsReportsFiltered = this.earningnsReports;  
      } else {
        this.earningnsReportsFiltered = this.earningnsReports.filter((item) => item.routeId == this.routeControl.value);
      }
    } else if ((this.initialDateControl.value == "" && this.finalDateControl.value != "") || (this.initialDateControl.value != "" && this.finalDateControl.value == "")) {
      this.hasError = true;
    } else {
      this.adminService.getEarningsByDateRange(this.initialDateControl.value, this.finalDateControl.value).subscribe(dataEarnings => {
        if (dataEarnings) {
          if (this.routeControl.value == "0") {
            this.earningnsReportsFiltered = dataEarnings;  
          } else {
            this.earningnsReportsFiltered = dataEarnings.filter((item) => item.routeId == this.routeControl.value);
          }
        }
      });
    }
  }

  clear(){
    this.initialDateControl.setValue('');
    this.finalDateControl.setValue('');
    this.routeControl.setValue('0');
    this.filter();
  }

  get initialDateControl(): FormControl {
    return this.filterForm.get('initialDateControl') as FormControl;
  }

  get finalDateControl(): FormControl {
    return this.filterForm.get('finalDateControl') as FormControl;
  }

  get routeControl(): FormControl {
    return this.filterForm.get('routeControl') as FormControl;
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

  toggleHasError() {
    this.hasError = false;
  }

}
