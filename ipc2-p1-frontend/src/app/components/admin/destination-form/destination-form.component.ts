import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Destination } from 'src/app/model/destination';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-destination-form',
  templateUrl: './destination-form.component.html',
  styleUrls: ['./destination-form.component.css']
})
export class DestinationFormComponent implements OnInit {

  @Input() destinationToUpdate!: Destination;
  @Input() isNewDestination!: boolean;
  @Output() toggleFormVisibility = new EventEmitter<void>();
  @Output() actionCompleted = new EventEmitter<void>();

  destinationToSend!: Destination;
  isDestinationCreated = false;
  isDestinationUpdated = false;
  hasError = false;
  message = '';

  destinationForm = new FormGroup({
    name: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    fee: new FormControl('', Validators.required),
  });

  constructor(private adminService: AdminService, private recepService: RecepService) { }

  ngOnInit(): void {
    if (!this.isNewDestination) {
      this.nameControl.setValue(this.destinationToUpdate.name);
      this.addressControl.setValue(this.destinationToUpdate.address);
      this.feeControl.setValue(this.destinationToUpdate.destinationFee);
    }
  }

  sendDestination() {
    if (this.validateFields()) {
      if (this.recepService.isNumber(this.feeControl.value) || this.recepService.isDecimal(this.feeControl.value)) {
        this.destinationToSend = new Destination();
        this.destinationToSend.name = this.nameControl.value;
        this.destinationToSend.address = this.addressControl.value;
        this.destinationToSend.destinationFee = this.feeControl.value;

        if (this.isNewDestination) {
          this.adminService.createDestination(this.destinationToSend).subscribe(dataDestination => {
            if (dataDestination) {
              this.message = "Destino creado exitosamente!";
              this.isDestinationCreated = true;
              this.actionCompleted.emit();
              this.clearForm();
            }
          });
        } else {
          this.destinationToSend.id = this.destinationToUpdate.id;
          this.adminService.updateDestination(this.destinationToSend).subscribe(dataDestination => {
            if (dataDestination) {
              this.message = "Destino editado exitosamente!";
              this.isDestinationUpdated = true;
              this.actionCompleted.emit();
            }
          });
        }
      } else {
        this.message = "La información ingresada no es válida";
        this.hasError = true;
      }
    } else {
      this.message = "Por favor complete todos los campos";
      this.hasError = true;
    }
  }

  get nameControl(): FormControl {
    return this.destinationForm.get('name') as FormControl;
  }

  get addressControl(): FormControl {
    return this.destinationForm.get('address') as FormControl;
  }

  get feeControl(): FormControl {
    return this.destinationForm.get('fee') as FormControl;
  }

  validateFields() {
    if (this.nameControl.value === '' || this.addressControl.value === '' || this.feeControl.value === '') {
      return false;
    }
    return true;
  }

  clearForm() {
    this.destinationForm.reset();
  }

  toggleHasError() {
    this.hasError = !this.hasError;
  }

  toggleActionCompleted() {
    this.isDestinationCreated = false;
  }

  closeForm() {
    this.toggleFormVisibility.emit();
  }
}
