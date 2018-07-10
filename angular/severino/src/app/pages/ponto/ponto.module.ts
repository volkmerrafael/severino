import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule } from "@angular/forms";
import { TableModule } from 'primeng/table';
import { PontoService } from "../../services/ponto.service";
import { PontoComponent } from "./ponto.component";
import { ButtonModule } from 'primeng/button';
import { FormatarDataPipe } from '../../components/pipes/pipe';
import { MultiSelectModule } from 'primeng/multiselect';
import { PanelModule } from 'primeng/panel';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { TooltipModule } from 'primeng/tooltip';
import { DialogModule } from 'primeng/dialog';
import { FormatarMinutosPipe } from '../../shared/components/pipes/time.pipe';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ListboxModule } from 'primeng/listbox';
import {AccordionModule} from 'primeng/accordion';

@NgModule({
  imports: [
    CommonModule,
    SelectButtonModule,
    FormsModule,
    TableModule,
    ButtonModule,
    MultiSelectModule,
    PanelModule,
    OverlayPanelModule,
    TooltipModule,
    DialogModule,
    InputTextareaModule,
    ListboxModule,
    AccordionModule
  ],
  declarations: [
    PontoComponent,
    FormatarDataPipe,
    FormatarMinutosPipe,
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
