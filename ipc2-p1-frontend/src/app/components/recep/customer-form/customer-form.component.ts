import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Customer } from 'src/app/model/customer';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-customer-form',
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.css'],
})
export class CustomerFormComponent implements OnInit {
  @Input() nitValue!: string;
  @Output() toggleFormVisibility = new EventEmitter<void>();

  customerToCreate !: Customer;
  onlyView = false;
  customerCreated = false;
  hasError = false;
  message = '';

  customerForm = new FormGroup({
    name: new FormControl('', Validators.required),
    sex: new FormControl('valInicial', Validators.required),
  });

  constructor(private recepService: RecepService) {
    this.customerToCreate = new Customer();
  }

  ngOnInit(): void {
    this.recepService.getCustomerByNit(this.nitValue).subscribe(customer => {
      if (customer) {
        this.formOnlyView(customer);
      }
    }, error => {
      console.log('nit nuevo:', error.status);
    });

  }

  get nameControl(): FormControl {
    return this.customerForm.get('name') as FormControl;
  }

  get sexControl(): FormControl {
    return this.customerForm.get('sex') as FormControl;
  }

  sendCustomer() {
    if (this.validateFields()) {
      this.customerToCreate.nit = this.nitValue;
      this.customerToCreate.name = this.nameControl.value;
      this.customerToCreate.sex = this.sexControl.value;

      this.recepService.createCustomer(this.customerToCreate).subscribe(customer => {
        if (customer) {
          this.customerCreated = !this.customerCreated;
          this.formOnlyView(customer);
        }
      }, error => {
        console.log('error al crear cliente:', error.status);
      });
    } else {
      this.message = "Complete los campos vacios";
      this.hasError = !this.hasError;
    }
  }

  validateFields() {
    if (this.nameControl.value === '' || this.sexControl.value === 'valInicial') {
      return false;
    }
    return true;
  }

  formOnlyView(customer: Customer) {
    this.nameControl.setValue(customer.name);
    this.sexControl.setValue(customer.sex);
    this.nameControl.disable();
    this.sexControl.disable();
    this.onlyView = !this.onlyView;
  }

  closeForm() {
    this.toggleFormVisibility.emit();
  }

}
