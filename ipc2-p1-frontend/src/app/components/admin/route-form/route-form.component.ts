import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Destination } from 'src/app/model/destination';
import { Route } from 'src/app/model/route';
import { TypeUser } from 'src/app/model/type-user';
import { User } from 'src/app/model/user';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-route-form',
  templateUrl: './route-form.component.html',
  styleUrls: ['./route-form.component.css']
})
export class RouteFormComponent {

  @Input() destinations!: Destination[];
  @Input() routeToUpdate!: Route;
  @Input() isNewRoute!: boolean;
  @Output() toggleFormVisibility = new EventEmitter<void>();
  @Output() actionCompleted = new EventEmitter<void>();

  routeToSend!: Route;
  isRouteCreated = false;
  isRouteUpdated = false;
  hasError = false;
  message = '';

  routeForm = new FormGroup({
    name: new FormControl('', Validators.required),
    destination: new FormControl('0', Validators.required),
  });

  constructor(private adminService: AdminService, private recepService: RecepService) { }

  ngOnInit(): void {
    if (!this.isNewRoute) {
      this.nameControl.setValue(this.routeToUpdate.name);
      this.destinationControl.setValue(this.routeToUpdate.destinationId);
    }
  }

  sendRoute() {
    if (this.validateFields()) {
      this.routeToSend = new Route();
      this.routeToSend.name = this.nameControl.value;
      this.routeToSend.destinationId = parseInt(this.destinationControl.value);

      if (this.isNewRoute) {
        this.adminService.createRoute(this.routeToSend).subscribe(dataRoute => {
          if (dataRoute) {
            this.message = "Ruta creada exitosamente!";
            this.isRouteCreated = true;
            this.clearForm();
            this.actionCompleted.emit();
          }
        });
      } else {
        this.routeToSend.id = this.routeToUpdate.id;
        this.routeToSend.active = this.routeToSend.active;
        this.adminService.updateRoute(this.routeToSend).subscribe(dataRoute => {
          if (dataRoute) {
            this.message = "Ruta editada exitosamente!";
            this.isRouteUpdated = true;
            this.actionCompleted.emit();
          }
        });
      }
    } else {
      this.message = "Por favor complete todos los campos";
      this.hasError = true;
    }
  }

  get nameControl(): FormControl {
    return this.routeForm.get('name') as FormControl;
  }

  get destinationControl(): FormControl {
    return this.routeForm.get('destination') as FormControl;
  }

  validateFields() {
    if (this.nameControl.value === '' || this.destinationControl.value === '0') {
      return false;
    }
    return true;
  }

  clearForm() {
    this.routeForm.reset();
    this.destinationControl.setValue('0');
  }

  toggleHasError() {
    this.hasError = !this.hasError;
  }

  closeForm() {
    this.toggleFormVisibility.emit();
  }

  toggleActionCompleted() {
    this.isRouteCreated = false;
  }
}
