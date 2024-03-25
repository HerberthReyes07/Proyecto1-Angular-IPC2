import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-package-entry',
  templateUrl: './package-entry.component.html',
  styleUrls: ['./package-entry.component.css']
})
export class PackageEntryComponent {
  packageEntryForm = new FormGroup({
    nit: new FormControl('', Validators.required),
    destination: new FormControl('valInicial'),
    weight: new FormControl('', Validators.required),
  });
  /* packageEntryForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.packageEntryForm = this.formBuilder.group({
      nit: new FormControl('', Validators.required),
      destination: new FormControl('holamundo', Validators.required),
      weight: new FormControl('', Validators.required),
    });
  } */

  get nitControl(): FormControl {
    return this.packageEntryForm.get('nit') as FormControl;
  }

  get destinationControl(): FormControl {
    return this.packageEntryForm.get('destination') as FormControl;
  }

  get weightControl(): FormControl {
    return this.packageEntryForm.get('weight') as FormControl;
  }
}
