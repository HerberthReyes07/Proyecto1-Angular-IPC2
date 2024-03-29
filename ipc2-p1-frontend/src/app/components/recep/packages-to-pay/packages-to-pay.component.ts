import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { Destination } from 'src/app/model/destination';
import { Package } from 'src/app/model/package';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-packages-to-pay',
  templateUrl: './packages-to-pay.component.html',
  styleUrls: ['./packages-to-pay.component.css']
})
export class PackagesToPayComponent {

  @Input() customerNit!: string;
  @Input() destinations!: Destination[];
  @Input() pricePerPound!: number;
  @Input() invoiceNo!: number;

  @Output() togglePayModalVisibility = new EventEmitter<void>();
  @Output() paymentMade = new EventEmitter<void>();

  listaSubscription!: Subscription;

  packages$ = this.recepService.packages$;
  total$ = this.recepService.total$;
  isPaymentMade = false;
  isPaymentAllowed = true;
  //showErrorMessage = false;
  //showSuccedMessage = false;
  //errorMessage = '';
  message = '';

  constructor(private recepService: RecepService) {
  }

  pay() {
    this.listaSubscription = this.recepService.packages$.subscribe((packages: Package[]) => {
      for (let index = 0; index < packages.length; index++) {
        this.recepService.createPackage(packages[index]).subscribe(createdPackage => {
          if (createdPackage) {
            console.log("PAQUETE CREADO: i: " + index + ", ci: " + packages[index].customerId + ", di: " + packages[index].destinationId
              + ", in: " + packages[index].invoiceNo + ", sc: " + packages[index].shippingCost + ", w:" + packages[index].weight);

            this.message = this.message + "El  estado del paquete #" + (index + 1) + " es: " + createdPackage.status + ", ";
            //this.deletePackage(packages[index]);
            if (index == (packages.length - 1)) {
              this.isPaymentMade = true;
              this.listaSubscription.unsubscribe();
              this.resetForm();
            }
          } else { }
        }, error => {
          console.log('Error al crear paquete: [' + index + '] ->', error.status);
        });
      }
    });
  }

  getDestinationById(destinationId: number) {

    for (let index = 0; index < this.destinations.length; index++) {
      if (this.destinations[index].id === destinationId) {
        return this.destinations[index];
      }
    }
    return this.destinations[0];

  }

  deletePackage(packageToDelete: Package): void {
    this.recepService.deletePackage(packageToDelete);

    this.recepService.packages$.subscribe((data: Package[]) => {
      if (data.length == 0) {
        this.isPaymentAllowed = false;
      }
    });
  }

  closeModal() {
    this.togglePayModalVisibility.emit();
    //this.paymentMade.emit();
  }

  resetForm() {
    this.paymentMade.emit();
  }
}
