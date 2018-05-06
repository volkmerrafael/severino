import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AdminComponent } from "./admin.component";
import { MeusAnunciosComponent } from "./meus-anuncios/meus-anuncios.component";
import { AdminCanActivate } from "./admin-can-activate";
import { MeuCadastroComponent } from "./meu-cadastro/meu-cadastro.component";

const rotas: Routes = [
  {
    path: '',
    component: AdminComponent,
    canActivate: [
      AdminCanActivate
    ],
    children: [
      { path: '', redirectTo: 'meus-anuncios' },
      { path: 'meus-anuncios', component: MeusAnunciosComponent },
      { path: 'meu-cadastro', component: MeuCadastroComponent }
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild( rotas )
  ],
  exports: [
    RouterModule
  ],
  providers: [
    AdminCanActivate
  ]
})
export class AdminRoutingModule {

}
