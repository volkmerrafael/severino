import { Component } from "@angular/core";
import { SelectItem } from 'primeng/api';
import { Ponto } from "../../../model/ponto";
import { AnuncioService } from "../../../services/anuncio.service";

@Component({
  selector: 'app-meus-anuncios',
  templateUrl: './meus-anuncios.component.html',
  styleUrls: ['./meus-anuncios.component.css']
})
export class MeusAnunciosComponent {

  status: SelectItem[];
  selectedStatus: string;
  pontos: Ponto[] = [];

  constructor(private anuncioService: AnuncioService) {
    this.status = [
        {label: 'Publicados', value: 'P'},
        {label: 'Aguardando publicação', value: 'A'},
        {label: 'Expirados', value: 'E'}
    ];
    this.selectedStatus = 'P';
    this.anuncioService
            .findAllByStatus( this.selectedStatus )
            .subscribe( res => this.pontos = res );
  }

  onOptionClick(event: any) {
    this.anuncioService
            .findAllByStatus( event.option.value )
            .subscribe( res => this.pontos = res );
  }

}
