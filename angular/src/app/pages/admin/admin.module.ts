import { NgModule } from "@angular/core";
import { AdminComponent } from "./admin.component";
import { AdminRoutingModule } from "./admin-routing.module";
import { MeusAnunciosModule } from "./meus-anuncios/meus-anuncios.module";
import { CommonModule } from "@angular/common";
import { HeaderModule } from "../../components/header/header.module";
import { MeuCadastroModule } from "./meu-cadastro/meu-cadastro.module";

@NgModule({
  imports: [
    CommonModule,
    MeusAnunciosModule,
    MeuCadastroModule,
    HeaderModule,
    AdminRoutingModule
  ],
  declarations: [
    AdminComponent
  ],
  exports: [
    AdminComponent
  ]
})
export class AdminModule {

}
