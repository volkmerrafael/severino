import { Usuario } from './../../shared/model/usuario';
import { ImportacaoService } from './../../services/importacao.service';
import { AnoMes } from './../../shared/model/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../shared/model/ponto';
import { ArquivoImportacao } from "../../shared/model/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../shared/model/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { DateFormatPipe } from '../../components/pipes/pipe';
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AdminComponent } from './admin.component';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

@NgModule({
    imports: [
      CommonModule,
      DropdownModule,
      FormsModule,
      BrowserModule,
      MenubarModule,
      FileUploadModule,
      TableModule,
      ButtonModule
    ],
    declarations: [
      AdminComponent
    ],
    exports: [
      AdminComponent
    ],
    providers: [
      ImportacaoService,
      PontoService
    ]
  })
export class AdminModule {

}
