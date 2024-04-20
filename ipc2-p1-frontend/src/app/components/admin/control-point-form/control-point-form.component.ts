import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ControlPoint } from 'src/app/model/control-point';
import { Parameter } from 'src/app/model/parameter';
import { Route } from 'src/app/model/route';
import { User } from 'src/app/model/user';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-control-point-form',
  templateUrl: './control-point-form.component.html',
  styleUrls: ['./control-point-form.component.css']
})
export class ControlPointFormComponent {

  @Input() routes!: Route[];
  @Input() operators!: User[];
  @Input() controlPointToUpdate!: ControlPoint;
  @Input() isNewControlPoint!: boolean;
  @Output() toggleFormVisibility = new EventEmitter<void>();
  @Output() actionCompleted = new EventEmitter<void>();

  parameter!: Parameter;
  isGlobalFee = false;
  controlPointToSend!: ControlPoint;
  isControlPointCreated = false;
  isControlPointUpdated = false;
  hasError = false;
  message = '';

  controlPointForm = new FormGroup({
    name: new FormControl('', Validators.required),
    queueCapacity: new FormControl('', Validators.required),
    operationFee: new FormControl('', Validators.required),
    route: new FormControl('0', Validators.required),
    operator: new FormControl('0', Validators.required),
  });

  constructor(private adminService: AdminService, private recepService: RecepService) { }

  ngOnInit(): void {
    this.recepService.getCurrentParameter().subscribe(dataParameter => {
      if (dataParameter) {
        this.parameter = dataParameter;
        if (!this.isNewControlPoint) {
          this.nameControl.setValue(this.controlPointToUpdate.name);
          this.queueCapacityControl.setValue(this.controlPointToUpdate.queueCapacity);
          this.routeControl.setValue(this.controlPointToUpdate.routeId);
          this.operatorControl.setValue(this.controlPointToUpdate.operatorId);
          if (this.parameter.globalOperationFee === this.controlPointToUpdate.localOperationFee) {
            this.isGlobalFee = true;
            this.globalFee();
          } else {
            this.operationFeeControl.setValue(this.controlPointToUpdate.localOperationFee);
          }
        }
      }
    });
  }

  sendRoute() {
    if (this.validateFields()) {
      if (this.recepService.isNumber(this.queueCapacityControl.value) &&
        (this.recepService.isNumber(this.operationFeeControl.value) || this.recepService.isDecimal(this.operationFeeControl.value))) {

        this.controlPointToSend = new ControlPoint();
        this.controlPointToSend.name = this.nameControl.value;
        this.controlPointToSend.queueCapacity = this.queueCapacityControl.value;
        this.controlPointToSend.localOperationFee = this.operationFeeControl.value;
        this.controlPointToSend.routeId = this.routeControl.value;
        this.controlPointToSend.operatorId = this.operatorControl.value;

        if (this.isNewControlPoint) {
          this.adminService.createControlPoint(this.controlPointToSend).subscribe(dataControlPoint => {
            if (dataControlPoint) {
              this.message = "Punto de Control creado exitosamente!";
              this.isControlPointCreated = true;
              this.actionCompleted.emit();
              this.clearForm();
            }
          }, error => {
            this.message = "El punto de control no se puede crear ya que la ruta seleccionada tiene paquetes en ruta";
            this.hasError = true;
          });
        } else {
          this.controlPointToSend.id = this.controlPointToUpdate.id;

          if (this.controlPointToUpdate.routeId === this.controlPointToSend.routeId) {
            this.controlPointToSend.orderNo = this.controlPointToUpdate.orderNo;
            this.updateControlPoint(false);
          } else {
            this.adminService.getNextOrderNo(this.controlPointToSend.routeId).subscribe(dataOrderNo => {
              if (dataOrderNo) {
                this.controlPointToSend.orderNo = dataOrderNo.orderNo;
                this.updateControlPoint(true);
              }
            });
          }
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

  updateControlPoint(updateControlPoints: boolean) {
    this.adminService.updateControlPoint(this.controlPointToSend).subscribe(dataRoute => {
      if (dataRoute) {
        if (updateControlPoints) {
          this.adminService.updateControlPoints(this.controlPointToUpdate).subscribe(dataControlPoints => {
            if (dataControlPoints) {
              this.updateCompleted();
            }
          });
        } else {
          this.updateCompleted();
        }
      }
    }, error => {
      this.message = "El punto de control " + this.controlPointToUpdate.name + " no se puede editar debido a que tiene paquetes en cola";
      this.hasError = true;
    });
  }
  
  updateCompleted(){
    this.message = "Punto de Control editado exitosamente!";
    this.isControlPointUpdated = true;
    this.actionCompleted.emit();
  }

  globalFee() {
    this.operationFeeControl.disable();
    this.operationFeeControl.setValue(this.parameter.globalOperationFee);
  }

  localFee() {
    this.operationFeeControl.enable();
    if (this.isNewControlPoint || this.isGlobalFee) {
      this.operationFeeControl.setValue('');
    } else {
      this.operationFeeControl.setValue(this.controlPointToUpdate.localOperationFee);
    }
  }

  get nameControl(): FormControl {
    return this.controlPointForm.get('name') as FormControl;
  }

  get queueCapacityControl(): FormControl {
    return this.controlPointForm.get('queueCapacity') as FormControl;
  }

  get operationFeeControl(): FormControl {
    return this.controlPointForm.get('operationFee') as FormControl;
  }

  get routeControl(): FormControl {
    return this.controlPointForm.get('route') as FormControl;
  }

  get operatorControl(): FormControl {
    return this.controlPointForm.get('operator') as FormControl;
  }

  validateFields() {
    if (this.nameControl.value === '' || this.queueCapacityControl.value === '' || this.operationFeeControl.value === '0' ||
      this.routeControl.value === '0' || this.operatorControl.value === '0') {
      return false;
    }
    return true;
  }

  clearForm() {
    this.controlPointForm.reset();
    this.routeControl.setValue('0');
    this.operatorControl.setValue('0');
  }

  toggleHasError() {
    this.hasError = !this.hasError;
  }

  closeForm() {
    this.toggleFormVisibility.emit();
  }

  toggleActionCompleted() {
    this.isControlPointCreated = false;
  }
}
