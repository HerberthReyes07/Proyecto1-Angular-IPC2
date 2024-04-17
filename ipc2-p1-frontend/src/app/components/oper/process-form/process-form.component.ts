import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Destination } from 'src/app/model/destination';
import { Package } from 'src/app/model/package';
import { ProcessDetail } from 'src/app/model/process-detail';
import { OperService } from 'src/app/service/oper.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-process-form',
  templateUrl: './process-form.component.html',
  styleUrls: ['./process-form.component.css']
})
export class ProcessFormComponent {

  @Input() packageToProcess!: Package;
  @Input() destinations!: Destination[];
  @Output() toggleFormVisibility = new EventEmitter<void>();
  @Output() processDone = new EventEmitter<void>();

  processDetail!: ProcessDetail;
  packageProcessed = false;
  hasError = false;
  message = "";

  processForm = new FormGroup({
    time: new FormControl('', Validators.required),
    processDate: new FormControl('', Validators.required),
  });

  constructor(private operService: OperService, private recepService: RecepService) { }

  processPackage() {

    if (this.timeControl.value === null || this.processDateControl.value === "") {
      this.message = "Complete todos los campos obligatorios";
      this.hasError = true;
    } else if (!this.recepService.isNumber(this.timeControl.value)) {
      this.message = "El tiempo ingresado no es válido";
      this.hasError = true;
    } else {

      this.processDetail = new ProcessDetail();
      this.processDetail.time = this.timeControl.value;
      this.processDetail.processDate = this.processDateControl.value;

      this.operService.processPackage(this.packageToProcess.id, this.processDetail).subscribe(dataProcessDetail => {
        if (dataProcessDetail) {
          this.packageProcessed = true;
          this.processDone.emit();
        }
      });
    }


  }

  get timeControl(): FormControl {
    return this.processForm.get('time') as FormControl;
  }

  get processDateControl(): FormControl {
    return this.processForm.get('processDate') as FormControl;
  }

  getDestinationById(destinationId: number) {
    for (let index = 0; index < this.destinations.length; index++) {
      if (this.destinations[index].id === destinationId) {
        return this.destinations[index];
      }
    }
    return this.destinations[0];
  }

  toggleHasError() {
    this.hasError = false;
  }

  closeForm() {
    this.toggleFormVisibility.emit();
  }

}
