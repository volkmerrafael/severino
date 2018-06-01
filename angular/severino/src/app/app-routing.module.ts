import { AdminComponent } from './pages/admin/admin.component';
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { PontoComponent } from "./pages/ponto/ponto.component";
import { HomeComponent } from "./pages/home/home.component";
import { AuthGuardService } from "./services/auth-guard.service"

const rotas: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'ponto', component: PontoComponent, canActivate: [AuthGuardService] },
  {path: 'admin', component: AdminComponent, canActivate: [AuthGuardService] },
  {path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [
    RouterModule.forRoot( rotas )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {

}
