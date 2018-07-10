import { Usuario } from './../../shared/models/usuario';
import { ImportacaoService } from './../../services/importacao.service';
import { AnoMes } from './../../shared/models/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../shared/models/ponto';
import { ArquivoImportacao } from "../../shared/models/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../shared/models/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { FormatarDataPipe } from '../../components/pipes/pipe';
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { MapaComponent } from './mapa.component';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { GMapModule } from 'primeng/gmap';
import { AgmCoreModule } from '@agm/core';

@NgModule({
    imports: [
      CommonModule,
      DropdownModule,
      FormsModule,
      BrowserModule,
      MenubarModule,
      FileUploadModule,
      TableModule,
      ButtonModule,
      PaginatorModule,
      AgmCoreModule.forRoot({
        // please get your own API key here:
        // https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en
        apiKey: 'xxx'
      })
    ],
    declarations: [
      MapaComponent
    ],
    exports: [
      MapaComponent
    ],
    providers: [
      ImportacaoService,
      PontoService
    ]
  })
export class MapaModule {

}
