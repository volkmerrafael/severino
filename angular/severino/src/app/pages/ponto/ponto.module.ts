import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule } from "@angular/forms";
import { TableModule } from 'primeng/table';
import { PontoService } from "../../services/ponto.service";
import { PontoComponent } from "./ponto.component";
import { ButtonModule } from 'primeng/button';
import { FormatarDataPipe } from '../../components/pipes/pipe';
import {MultiSelectModule} from 'primeng/multiselect';
import {PanelModule} from 'primeng/panel';

@NgModule({
  imports: [
    CommonModule,
    SelectButtonModule,
    FormsModule,
    TableModule,
    ButtonModule,
    MultiSelectModule,
    PanelModule
  ],
  declarations: [
    PontoComponent,
    FormatarDataPipe
  ],
  exports: [
    PontoComponent
  ],
  providers: [
    PontoService
  ]
})
export class PontoModule {

}
