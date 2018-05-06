import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { SenhaComponent } from "./pages/senha/senha.component";
import { CadastroComponent } from "./pages/cadastro/cadastro.component";

const rotas: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'senha', component: SenhaComponent},
  {path: 'cadastro', component: CadastroComponent},
  {path: 'admin', loadChildren: 'app/pages/admin/admin.module#AdminModule'},
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
