import { AdminComponent } from './pages/admin/admin.component';
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { PontoComponent } from "./pages/ponto/ponto.component";

const rotas: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'ponto', component: PontoComponent },
  {path: 'admin', component: AdminComponent },
  {path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [
    RouterModule.forRoot( rotas, { useHash: true } )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {

}
