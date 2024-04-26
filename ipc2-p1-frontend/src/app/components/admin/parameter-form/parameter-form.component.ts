import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Parameter } from 'src/app/model/parameter';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-parameter-form',
  templateUrl: './parameter-form.component.html',
  styleUrls: ['./parameter-form.component.css']
})
export class ParameterFormComponent implements OnInit{

  @Input() currentParameter!: Parameter;
  @Output() toggleFormVisibility = new EventEmitter<void>();
  @Output() actionCompleted = new EventEmitter<void>();

  parameterToSend!: Parameter;
  isParameterCreated = false;
  hasError = false;
  message = '';

  parameterForm = new FormGroup({
    globalFee: new FormControl('', Validators.required),
    pricePerPound: new FormControl('', Validators.required),
  });

  ngOnInit(): void {
    this.globalFeeControl.setValue(this.currentParameter.globalOperationFee);
    this.pricePerPoundControl.setValue(this.currentParameter.pricePerPound);
  }

  constructor(private adminService: AdminService, private recepService: RecepService) { }

  sendParameter() {
    if (this.validateFields()) {
      if ((this.recepService.isNumber(this.globalFeeControl.value) || this.recepService.isDecimal(this.globalFeeControl.value))
        && (this.recepService.isNumber(this.pricePerPoundControl.value) || this.recepService.isDecimal(this.pricePerPoundControl.value))) {

        this.parameterToSend = new Parameter();
        this.parameterToSend.globalOperationFee = this.globalFeeControl.value;
        this.parameterToSend.pricePerPound = this.pricePerPoundControl.value;

        this.adminService.createParameter(this.parameterToSend).subscribe(dataParameter => {
          if (dataParameter) {
            this.message = "Parametro del sistema creado y actualizado exitosamente!";
            this.isParameterCreated = true;
            this.actionCompleted.emit();
            this.clearForm();
          }
        });

      } else {
        this.message = "La información ingresada no es válida";
        this.hasError = true;
      }
    } else {
      this.message = "Por favor complete todos los campos";
      this.hasError = true;
    }
  }

  get globalFeeControl(): FormControl {
    return this.parameterForm.get('globalFee') as FormControl;
  }

  get pricePerPoundControl(): FormControl {
    return this.parameterForm.get('pricePerPound') as FormControl;
  }

  validateFields() {
    if (this.globalFeeControl.value === '' || this.pricePerPoundControl.value === '') {
      return false;
    }
    return true;
  }

  clearForm() {
    this.parameterForm.reset();
  }

  toggleHasError() {
    this.hasError = !this.hasError;
  }

  toggleActionCompleted() {
    this.isParameterCreated = false;
  }

  closeForm() {
    this.toggleFormVisibility.emit();
  }
}
