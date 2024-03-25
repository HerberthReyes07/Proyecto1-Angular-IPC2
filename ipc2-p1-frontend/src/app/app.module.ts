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
    HomeOperComponent
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
