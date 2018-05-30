import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule } from "@angular/forms";
import { TableModule } from 'primeng/table';
import { PontoService } from "../../services/ponto.service";
import { PontoComponent } from "./ponto.component";
import { ButtonModule } from 'primeng/button';
import { FormControl } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms'; 
import { FormGroup } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    SelectButtonModule,
    FormsModule,
    TableModule,
    ButtonModule,
    ReactiveFormsModule
  ],
  declarations: [
    PontoComponent
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
