import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeAdminComponent } from './components/admin/home-admin/home-admin.component';
import { HomeOperComponent } from './components/oper/home-oper/home-oper.component';
import { HomeRecepComponent } from './components/recep/home-recep/home-recep.component';
import { IsLoggedInGuard } from './guard/is-logged-in.guard';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ManagerHomepageComponent } from './components/manager-homepage/manager-homepage.component';
import { PackageEntryComponent } from './components/recep/package-entry/package-entry.component';
import { PickUpPackageComponent } from './components/recep/pick-up-package/pick-up-package.component';
import { CheckLocationComponent } from './components/recep/check-location/check-location.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/homepage',
    pathMatch: 'full'
  },
  {
    path: 'homepage',
    component: ManagerHomepageComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admin/home',
    component: HomeAdminComponent,
    canActivate: [IsLoggedInGuard]
  },
  {
    path: 'oper/home',
    component: HomeOperComponent,
    canActivate: [IsLoggedInGuard]
  },
  {
    path: 'recep/home',
    component: HomeRecepComponent,
    canActivate: [IsLoggedInGuard]
  },
  {
    path: 'recep/paquetes/entrada',
    component: PackageEntryComponent,
    canActivate: [IsLoggedInGuard]
  },
  {
    path: 'recep/paquetes/retiro',
    component: PickUpPackageComponent,
    canActivate: [IsLoggedInGuard]
  },
  {
    path: 'recep/paquetes/localizacion',
    component: CheckLocationComponent,
    canActivate: [IsLoggedInGuard]
  },
  {
    path: '**',
    component: PageNotFoundComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
