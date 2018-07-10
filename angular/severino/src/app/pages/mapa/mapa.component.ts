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
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TratamentoErrosService } from '../../services/tratamento-erros.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { Message } from 'primeng/components/common/message';
import { ConfiguracaoIntegracao } from "../../shared/models/configuracaoIntegracao";
import { UsuarioService } from "../../services/usuario.service";
import { Usuario1Service } from "../../modules/usuario/usuario.service";

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})
export class MapaComponent implements OnInit {

  usuarios: any;
  configuracao: ConfiguracaoIntegracao = new ConfiguracaoIntegracao();
  
  mensagemGrow: any;
  tituloGrow: any;
  tipoGrow: any;
  token: any;
  msgs: Message[];
  cols: any[];

  //maps
  zoom: number = 14;
  // initial center position for the map
  lat: number;
  lng: number;
    
  constructor(
    private importacaoService: ImportacaoService,
    private tratamentoErrosService: TratamentoErrosService,
    private messageService: MessageService,
    private usuarioService: Usuario1Service,
  ) {
    //this.usuario.nome = sessionStorage.getItem('nomeUsuario');
  }

  ngOnInit() {
  
   this.usuarioService.getConfiguracao()
    .subscribe(res => {
      res.forEach( item => {
        this.configuracao = item;
      });
    });

    lat: this.configuracao.latitude;
    lng: this.configuracao.longitude;
    //maps

    //for (const usuario of usuario) {
     // this.uploadedFiles.push(file);
    //}

  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

}
