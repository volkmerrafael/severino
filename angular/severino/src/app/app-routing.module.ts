import { AdminComponent } from './pages/admin/admin.component';
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { PontoComponent } from "./pages/ponto/ponto.component";
import { HomeComponent } from "./pages/home/home.component";
import { PerfilComponent } from './pages/perfil/perfil.component';
import { EditarPerfilComponent } from './pages/editar-perfil/editar-perfil.component';
import { EditarTipoEventoComponent } from './pages/editar-tipo-evento/editar-tipo-evento.component';
import { AuthGuardService } from "./services/auth-guard.service";
import { ListaComponent } from "./pages/lista/lista.component";
import { DashboardComponent } from './modules/jira/pages/dashboard/dashboard.component';
import { DeclaracaoComponent } from './modules/usuario/pages/declaracao/declaracao.component';
import { ConfiguracaoComponent } from './modules/usuario/pages/configuracao/configuracao.component';
import { FeedbackComponent } from './pages/feedback/feedback.component';
import { VisualizaFeedbackComponent } from './pages/visualiza-feedback/visualiza-feedback.component';
import { ListaTipoEventoComponent } from './pages/listatipoevento/listatipoevento.component';
import { ListaFuncaoComponent } from './pages/listafuncao/listafuncao.component';
import { ListaDepartamentoComponent } from './pages/listadepartamento/listadepartamento.component';
import { ListaEmpresaComponent } from './pages/listaempresa/listaempresa.component';

const id: Number = 0;

const rotas: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'ponto', component: PontoComponent, canActivate: [AuthGuardService] },
  {path: 'admin', component: AdminComponent, canActivate: [AuthGuardService] },
  {path: 'perfil', component: PerfilComponent, canActivate: [AuthGuardService] },
  {path: 'editar-perfil', component: EditarPerfilComponent, canActivate: [AuthGuardService] },
  {path: 'editar-tipo-evento', component: EditarTipoEventoComponent, canActivate: [AuthGuardService] },
  {path: 'lista', component: ListaComponent, canActivate: [AuthGuardService] },
  {path: 'lista/tipoevento', component: ListaTipoEventoComponent, canActivate: [AuthGuardService] },
  {path: 'lista/funcao', component: ListaFuncaoComponent, canActivate: [AuthGuardService] },
  {path: 'lista/departamento', component: ListaDepartamentoComponent, canActivate: [AuthGuardService] },
  {path: 'lista/empresa', component: ListaEmpresaComponent, canActivate: [AuthGuardService] },
  {path: 'jira/dashboard', component: DashboardComponent},
  {path: 'usuario/ponto/declaracao', component: DeclaracaoComponent},
  {path: 'admin/configuracao', component: ConfiguracaoComponent},
  {path: 'jira', redirectTo: 'jira/dashboard'},
  {path: 'feedback', component: FeedbackComponent},
  {path: 'visualiza-feedback', component: VisualizaFeedbackComponent},
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
