import { NgModule } from "@angular/core";
import { SenhaComponent } from "./senha.component";
import { BrowserModule } from "@angular/platform-browser";
import { HeaderModule } from "../../components/header/header.module";
import { TituloModule } from "../../components/titulo/titulo.module";
import { CardModule } from "../../components/card/card.module";
import { FormsModule } from "@angular/forms";

@NgModule({
  imports: [
    BrowserModule,
    HeaderModule,
    TituloModule,
    CardModule,
    FormsModule
  ],
  declarations: [
    SenhaComponent
  ],
  exports: [
    SenhaComponent
  ]
})
export class SenhaModule {}
