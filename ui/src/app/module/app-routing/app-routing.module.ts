import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AppRoute} from "./app-route";
import {LoginComponent} from "../../component/login/login.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: AppRoute.login},
  {path: AppRoute.login, component: LoginComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {

}
