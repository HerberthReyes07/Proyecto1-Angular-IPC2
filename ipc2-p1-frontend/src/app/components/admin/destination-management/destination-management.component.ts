import { Component, OnInit } from '@angular/core';
import { Destination } from 'src/app/model/destination';
import { RecepService } from 'src/app/service/recep.service';

@Component({
  selector: 'app-destination-management',
  templateUrl: './destination-management.component.html',
  styleUrls: ['./destination-management.component.css']
})
export class DestinationManagementComponent implements OnInit{

  destinations!: Destination[];
  destinationsFiltered!: Destination[];

  isDestinationFormOpened = false;
  destinationToUpdate!: Destination;
  isNewDestination = true;

  ngOnInit(): void {
    this.recepService.getAllDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.destinationsFiltered = dataDestinations;
      }
    });
  }

  constructor(private recepService: RecepService) { }

  newDestination(){
    this.isNewDestination = true;
    this.toggleDestinationFormVisibility();
  }

  updateDestination(destination: Destination){
    this.isNewDestination = false;
    this.destinationToUpdate = destination;
    this.toggleDestinationFormVisibility();
  }

  filter(){
    const selectElement = document.getElementById('orderBy') as HTMLSelectElement;

    if (selectElement.value === "0") {
      this.destinationsFiltered = this.destinations;
    } else {
      if (selectElement.value === "1") {
        this.destinationsFiltered = [... this.destinations].sort(this.orderFeeDesc);
      } else {
        this.destinationsFiltered = [... this.destinations].sort(this.orderFeeAsc);
      }
    }
  }

  toggleDestinationFormVisibility() {
    this.isDestinationFormOpened = !this.isDestinationFormOpened;
  }

  actionCompleted(){
    this.recepService.getAllDestinations().subscribe(dataDestinations => {
      if (dataDestinations) {
        this.destinations = dataDestinations;
        this.filter();
      }
    });
  }

  orderFeeAsc(a: Destination, b: Destination) {
    return a.destinationFee - b.destinationFee;
  }

  orderFeeDesc(a: Destination, b: Destination) {
    return b.destinationFee - a.destinationFee;
  }
}
