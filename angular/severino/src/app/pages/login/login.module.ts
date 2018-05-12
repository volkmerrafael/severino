import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { LoginComponent } from "./login.component";
import { TituloModule } from "../../components/titulo/titulo.module";
import { CardModule } from "../../components/card/card.module";
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { UsuarioService } from "../../services/usuario.service";

@NgModule({
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,
    TituloModule,
    CardModule
  ],
  declarations: [
    LoginComponent
  ],
  exports: [
    LoginComponent
  ],
  providers: [
    UsuarioService
  ]
})
export class LoginModule {

}
