import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { LoginComponent } from "./login.component";
import { TituloModule } from "../../components/titulo/titulo.module";
import { CardModule } from "../../components/card/card.module";
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { UsuarioService } from "../../services/usuario.service";
import { MessageService } from 'primeng/components/common/messageservice';
import { TratamentoErrosService } from "../../services/tratamento-erros.service";
import {GrowlModule} from 'primeng/growl';

@NgModule({
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,
    TituloModule,
    CardModule,
    GrowlModule
  ],
  declarations: [
    LoginComponent
  ],
  exports: [
    LoginComponent
  ],
  providers: [
    UsuarioService,
    TratamentoErrosService,
    MessageService
  ]
})
export class LoginModule {

}
