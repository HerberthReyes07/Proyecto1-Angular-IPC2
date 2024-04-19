import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Destination } from 'src/app/model/destination';
import { Package } from 'src/app/model/package';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-package-entry',
  templateUrl: './package-entry.component.html',
  styleUrls: ['./package-entry.component.css']
})
export class PackageEntryComponent implements OnInit {

  destionation !: Destination;
  package !: Package;
  pricePerPound !: number;
  invoiceNo !: number;

  hasError = false;
  packageCreated = false;
  isCustomerFormOpened = false;
  isPaymentModalOpened = false;
  isPaymentDisabled = true;
  message = '';

  destinations: Destination[];
  destination: Destination;

  packageEntryForm = new FormGroup({
    nit: new FormControl('', [Validators.required, Validators.minLength(7), Validators.maxLength(7)]),
    destination: new FormControl('valInicial', Validators.required),
    weight: new FormControl('', Validators.required),
    entryDate: new FormControl('', Validators.required),
  });

  constructor(private recepService: RecepService) {
    this.package = new Package();
    this.destinations = new Array();
    this.destination = new Destination();
  }

  ngOnInit(): void {
    this.recepService.getValidDestinations()
      .subscribe(data => this.destinations = data);
    this.recepService.cleanPackages();
  }

  get nitControl(): FormControl {
    return this.packageEntryForm.get('nit') as FormControl;
  }

  get destinationControl(): FormControl {
    return this.packageEntryForm.get('destination') as FormControl;
  }

  get weightControl(): FormControl {
    return this.packageEntryForm.get('weight') as FormControl;
  }

  get entryDateControl(): FormControl {
    return this.packageEntryForm.get('entryDate') as FormControl;
  }

  toggleCustomerFormVisibility() {
    this.isCustomerFormOpened = !this.isCustomerFormOpened;
  }

  togglePayModalVisibility() {
    this.isPaymentModalOpened = !this.isPaymentModalOpened;
  }

  verifyNitToSend() {
    if (this.recepService.isNumber(this.nitControl.value)) {
      this.toggleCustomerFormVisibility();
    } else {
      this.message = "El nit a verificar/crear no es valido";
      this.hasError = !this.hasError;
    }
  }

  addPackage(): void {

    if (this.validateFields()) {
      this.recepService.getCustomerByNit(this.nitControl.value).subscribe(customer => {
        if (customer) {
          const selectedDestination = this.destinations.find(option => option.id === Number.parseInt(this.destinationControl.value));
          if (selectedDestination) {
            this.recepService.getCurrentParameter().subscribe(parameter => {
              this.recepService.getInvoiceNo().subscribe(auxPackage => {
                if (auxPackage) {

                  this.pricePerPound = parameter.pricePerPound;
                  this.invoiceNo = auxPackage.invoiceNo;

                  this.package.customerId = customer.id;
                  this.package.destinationId = selectedDestination.id;
                  this.package.weight = this.weightControl.value;
                  this.package.entryDate = this.entryDateControl.value;
                  this.package.shippingCost = this.recepService.calculateDestinationFee(selectedDestination, this.package, parameter);
                  this.package.invoiceNo = auxPackage.invoiceNo;

                  this.recepService.addPackage({ ...this.package });
                  this.isPaymentDisabled = false;

                  const lastNit = this.nitControl.value;
                  const lastDate = this.entryDateControl.value;
                  this.clearForm();
                  this.nitControl.setValue(lastNit);
                  this.nitControl.disable();
                  this.entryDateControl.setValue(lastDate);
                  this.entryDateControl.disable();
                }
              }, error => {
                console.log("Error al consultar invoiceNo")
              });
            });
          }
        }
      }, error => {
        console.log('nit invalido:', error.status);
        this.message = "El nit ingresado no existe, porfavor registre al cliente";
        this.hasError = !this.hasError;
      });
    } else {
      console.log("Campos vacios");
      this.message = "Complete los campos vacios para enviar el paquete";
      this.hasError = !this.hasError;
    }

  }

  validateFields() {
    if (this.nitControl.value === '' || this.destinationControl.value === 'valInicial' || this.weightControl.value === '') {
      return false;
    }
    return true;
  }

  clearForm() {
    this.packageEntryForm.reset();
    this.destinationControl.setValue('valInicial');
  }

  clearInfo() {
    this.clearForm();
    this.nitControl.enable();
    this.isPaymentDisabled = !this.isPaymentDisabled;
    this.recepService.cleanPackages();
  }

  /*isNumber(str: string): boolean {
    return /^\d+$/.test(str);
  }*/

}
