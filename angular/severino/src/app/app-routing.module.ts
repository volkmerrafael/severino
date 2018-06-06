import { AdminComponent } from './pages/admin/admin.component';
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { PontoComponent } from "./pages/ponto/ponto.component";
import { HomeComponent } from "./pages/home/home.component";
import { PerfilComponent } from './pages/perfil/perfil.component';
import { EditarPerfilComponent } from './pages/editar-perfil/editar-perfil.component';
import { AuthGuardService } from "./services/auth-guard.service";
import { ListaComponent } from "./pages/lista/lista.component";

const id: Number = 0;

const rotas: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'ponto', component: PontoComponent, canActivate: [AuthGuardService] },
  {path: 'admin', component: AdminComponent, canActivate: [AuthGuardService] },
  {path: 'perfil', component: PerfilComponent, canActivate: [AuthGuardService] },
  {path: 'editar-perfil', component: EditarPerfilComponent, canActivate: [AuthGuardService] },
  {path: 'lista', component: ListaComponent, canActivate: [AuthGuardService] },
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
