
import { Component } from '@angular/core';
import { MouseEvent } from '@agm/core';
import { OnInit } from "@angular/core";
import { ImportacaoService } from './../../services/importacao.service';
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
import { Usuario1Service } from "../../modules/usuario/usuario.service";
import { UsuarioService } from "../../services/usuario.service";
import { Usuario } from "../../shared/models/usuario";
import { Cordenada } from "../../shared/models/cordenada";

@Component({
  selector: 'mapa',
  templateUrl: './mapa.component.html',
  styleUrls: [ './mapa.component.css' ]
})
export class MapaComponent implements OnInit {
  
  configuracao: ConfiguracaoIntegracao = new ConfiguracaoIntegracao();
  usuario: Usuario = new Usuario();
  cordenada: Cordenada = new Cordenada();
  listaCoordenada: Cordenada[] = [];

=======
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
    private usuario1Service: Usuario1Service,
    private usuarioService: UsuarioService,
    private usuarioService: Usuario1Service
  ) {
    //this.usuario.nome = sessionStorage.getItem('nomeUsuario');
  }
  // initial center position for the map
  lat: number = -26.225501;
  lng: number = -52.6698741;

  ngOnInit() {
  
   this.usuarioService.listaUsuarios()
    .subscribe(res => {
      res.forEach( item => {
        if (item.latitude && item.longitude) {
          this.cordenada = new Cordenada;
          this.cordenada.lat = item.latitude;
          this.cordenada.lng = item.longitude;
          this.cordenada.draggable = true;
          this.cordenada.label = item.nome;
          console.log(this.cordenada);
          this.listaCoordenada.push(this.cordenada);
        }
      });
    });

   this.usuario1Service.getConfiguracao()
    .subscribe(res => {
      res.forEach( item => {
        this.configuracao = item;
        console.log(this.configuracao)
      });
    });

  }
  
  // google maps zoom level
  zoom: number = 11;

  clickedMarker(label: string, index: number) {
    console.log(`clicked the marker: ${label || index}`)
  }
  
  mapClicked($event: MouseEvent) {
    this.markers.push({
      lat: $event.coords.lat,
      lng: $event.coords.lng,
      draggable: true
    });
  }
  
  markerDragEnd(m: marker, $event: MouseEvent) {
    console.log('dragEnd', m, $event);
  }

  markers: marker[] = this.listaCoordenada;
/*  markers: marker[] = [
	  {
		  lat: -26.2247791,
		  lng: -52.6695751,
		  label: 'R',
		  draggable: true
	  },
	  {
		  lat: -26.2247791,
		  lng: -52.8995989,
		  label: 'T',
		  draggable: false
	  },
	  {
		  lat: -26.219361,
		  lng: -52.666191,
		  label: 'J',
		  draggable: false
	  },
	  {
		  lat: -26.422703,
		  lng: -52.900114,
		  label: 'SL',
		  draggable: false
	  },
	  {
		  lat: -26.270636,
		  lng: -52.675223,
		  label: 'JUL',
		  draggable: false
	  }
  ]*/

}

// just an interface for type safety.
interface marker {
	lat: number;
	lng: number;
	label?: string;
	draggable: boolean;
}
