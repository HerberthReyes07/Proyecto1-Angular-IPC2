import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { ReactiveFormsModule } from '@angular/forms';
import { FooterComponent } from './components/footer/footer.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ManagerHomepageComponent } from './components/manager-homepage/manager-homepage.component';
import { LoginComponent } from './components/login/login.component';
import { HomeRecepComponent } from './components/recep/home-recep/home-recep.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeAdminComponent } from './components/admin/home-admin/home-admin.component';
import { HomeOperComponent } from './components/oper/home-oper/home-oper.component';
import { PackageEntryComponent } from './components/recep/package-entry/package-entry.component';
import { CustomerFormComponent } from './components/recep/customer-form/customer-form.component';
import { PackagesToPayComponent } from './components/recep/packages-to-pay/packages-to-pay.component';
import { PickUpPackageComponent } from './components/recep/pick-up-package/pick-up-package.component';
import { CheckLocationComponent } from './components/recep/check-location/check-location.component';
import { ProcessPackagesComponent } from './components/oper/process-packages/process-packages.component';
import { ProcessFormComponent } from './components/oper/process-form/process-form.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    LoginComponent,
    PageNotFoundComponent,
    ManagerHomepageComponent,
    HomeRecepComponent,
    NavbarComponent,
    HomeAdminComponent,
    HomeOperComponent,
    PackageEntryComponent,
    CustomerFormComponent,
    PackagesToPayComponent,
    PickUpPackageComponent,
    CheckLocationComponent,
    ProcessPackagesComponent,
    ProcessFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
