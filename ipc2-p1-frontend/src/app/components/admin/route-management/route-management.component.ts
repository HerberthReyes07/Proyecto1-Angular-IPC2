import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Destination } from 'src/app/model/destination';
import { Route } from 'src/app/model/route';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-route-management',
  templateUrl: './route-management.component.html',
  styleUrls: ['./route-management.component.css']
})
export class RouteManagementComponent implements OnInit {

  routes!: Route[];
  routesFiltered!: Route[];
  destinations!: Destination[];
  
  isRouteFormOpened = false;
  routeToUpdate!: Route;
  isNewRoute = true;
  message = "";
  canProceed = false;
  deactivateError = false; 

  filterForm = new FormGroup({
    stateControl: new FormControl('0'),
    destinationControl: new FormControl('0'),
  });

  ngOnInit(): void {
    this.recepService.getAllDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.recepService.getAllRoutes().subscribe(dataRoutes => {
          if (dataRoutes) {
            this.routes = dataRoutes;
            this.routesFiltered = dataRoutes;
          }
        });
      }
    });
  }

  constructor(private adminService: AdminService, private recepService: RecepService) { }

  filter(){

    if (this.stateControl.value === "0") {
      if (this.destinationControl.value === "0") {
        this.routesFiltered = this.routes;  
      } else {
        this.routesFiltered = this.routes.filter((item) => item.destinationId === parseInt(this.destinationControl.value));  
      }
    } else if (this.stateControl.value === "1"){
      if (this.destinationControl.value === "0") {
        this.routesFiltered = this.routes.filter((item) => item.active === true);
      } else {
        this.routesFiltered = this.routes.filter((item) => (item.active === true && item.destinationId === parseInt(this.destinationControl.value)));  
      }
    } else if (this.stateControl.value === "2"){
      if (this.destinationControl.value === "0") {
        this.routesFiltered = this.routes.filter((item) => item.active === false);
      } else {
        this.routesFiltered = this.routes.filter((item) => (item.active === false && item.destinationId === parseInt(this.destinationControl.value)));  
      }
    }
  }

  newRoute(){
    this.isNewRoute = true;
    this.toggleRouteFormVisibility();
  }

  updateRoute(route: Route){
    this.isNewRoute = false;
    this.routeToUpdate = route;
    this.toggleRouteFormVisibility();
  }

  changeRouteStatus(route: Route){
    if (route.active === false) {
      route.active = true;
    } else {
      route.active = false;
    }
    this.adminService.updateRoute(route).subscribe(dataRoute => {
      if (dataRoute) {
        if (!dataRoute.active) {
          this.message = "La ruta: " + route.name + " ha sido desactivada";  
        } else {
          this.message = "La ruta: " + route.name + " ha sido activada";
        } 
        this.canProceed = true;
        this.filter();
      }
    }, error => {
      this.message = "No se puede desactivar la ruta: " + route.name + " ya que tiene paquetes en ruta";
      this.deactivateError = true;
      route.active = true;
    });
  }

  toggleRouteFormVisibility() {
    this.isRouteFormOpened = !this.isRouteFormOpened;
  }

  actionCompleted(){
    this.recepService.getAllRoutes().subscribe(dataRoutes => {
      if (dataRoutes) {
        this.routes = dataRoutes;
        this.filter();
      }
    });
  }

  get stateControl(): FormControl {
    return this.filterForm.get('stateControl') as FormControl;
  }

  get destinationControl(): FormControl {
    return this.filterForm.get('destinationControl') as FormControl;
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

  toggleCanProceed(){
    this.canProceed = !this.canProceed;
  }

  toggleDeactivateError(){
    this.deactivateError = !this.deactivateError;
  }
}
