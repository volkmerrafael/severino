import { NgModule } from "@angular/core";
import { MeusAnunciosComponent } from "./meus-anuncios.component";
import { CommonModule } from "@angular/common";
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule } from "@angular/forms";
import { TableModule } from 'primeng/table';
import { AnuncioService } from "../../../services/anuncio.service";
import { StatusPipe } from "../../../pipes/status.pipe";

@NgModule({
  imports: [
    CommonModule,
    SelectButtonModule,
    FormsModule,
    TableModule
  ],
  declarations: [
    MeusAnunciosComponent,
    StatusPipe
  ],
  exports: [
    MeusAnunciosComponent
  ],
  providers: [
    AnuncioService
  ]
})
export class MeusAnunciosModule {

}
