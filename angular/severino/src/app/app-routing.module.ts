import { AdminComponent } from './pages/admin/admin.component';
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { PontoComponent } from "./pages/ponto/ponto.component";

const rotas: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: 'home' }
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
