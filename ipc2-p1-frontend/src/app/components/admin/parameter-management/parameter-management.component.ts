import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Parameter } from 'src/app/model/parameter';
import { AdminService } from 'src/app/service/admin.service';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-parameter-management',
  templateUrl: './parameter-management.component.html',
  styleUrls: ['./parameter-management.component.css']
})
export class ParameterManagementComponent implements OnInit {

  currentParameter!: Parameter;
  parameters!: Parameter[];
  parametersFiltered!: Parameter[];

  isParameterFormOpened = false;

  filterForm = new FormGroup({
    globalFeeControl: new FormControl('0'),
    pricePerPoundControl: new FormControl('0'),
  });

  ngOnInit(): void {

    this.recepService.getCurrentParameter().subscribe(dataCurrentParameter => {
      if (dataCurrentParameter) {
        this.currentParameter = dataCurrentParameter;
        this.adminService.getAllParameters().subscribe(dataParameters => {
          if (dataParameters) {
            this.parameters = dataParameters;
            this.parametersFiltered = dataParameters;
          }
        });
      }
    });
  }

  constructor(private adminService: AdminService, private recepService: RecepService) { }

  newParameter() {
    this.toggleDestinationFormVisibility();
  }

  filter(){
    if (this.globalFeeControl.value !== "0") {
      this.filterByGlobalFee();
    } else {
      this.filterByPricePerPound();
    }
  }

  filterByGlobalFee() {

    if (this.pricePerPoundControl.value !== "0") {
      this.pricePerPoundControl.setValue("0");
    }

    if (this.globalFeeControl.value === "0") {
      this.parametersFiltered = this.parameters;
    } else {
      if (this.globalFeeControl.value === "1") {
        this.parametersFiltered = [... this.parameters].sort(this.orderGlobalFeeDesc);
      } else {
        this.parametersFiltered = [... this.parameters].sort(this.orderGlobalFeeAsc);
      }
    }
  }

  filterByPricePerPound() {

    if (this.globalFeeControl.value !== "0") {
      this.globalFeeControl.setValue("0");
    }

    if (this.pricePerPoundControl.value === "0") {
      this.parametersFiltered = this.parameters;
    } else {
      if (this.pricePerPoundControl.value === "1") {
        this.parametersFiltered = [... this.parameters].sort(this.orderPricePerPoundDesc);
      } else {
        this.parametersFiltered = [... this.parameters].sort(this.orderPricePerPoundAsc);
      }
    }
  }

  toggleDestinationFormVisibility() {
    this.isParameterFormOpened = !this.isParameterFormOpened;
  }

  actionCompleted() {
    this.recepService.getCurrentParameter().subscribe(dataCurrentParameter => {
      if (dataCurrentParameter) {
        this.currentParameter = dataCurrentParameter;
        this.adminService.getAllParameters().subscribe(dataParameters => {
          if (dataParameters) {
            this.parameters = dataParameters;
            this.filter();
          }
        });
      }
    });
  }

  get globalFeeControl(): FormControl {
    return this.filterForm.get('globalFeeControl') as FormControl;
  }

  get pricePerPoundControl(): FormControl {
    return this.filterForm.get('pricePerPoundControl') as FormControl;
  }

  orderGlobalFeeAsc(a: Parameter, b: Parameter) {
    return a.globalOperationFee - b.globalOperationFee;
  }

  orderGlobalFeeDesc(a: Parameter, b: Parameter) {
    return b.globalOperationFee - a.globalOperationFee;
  }

  orderPricePerPoundAsc(a: Parameter, b: Parameter) {
    return a.pricePerPound - b.pricePerPound;
  }

  orderPricePerPoundDesc(a: Parameter, b: Parameter) {
    return b.pricePerPound - a.pricePerPound;
  }


}
