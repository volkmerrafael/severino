import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../model/ponto';

@Component({
  selector: 'app-ponto',
  templateUrl: './ponto.component.html',
  styleUrls: ['./ponto.component.css']
})
export class PontoComponent implements OnInit {

  mes : String = "05";
  ano : String = "2018";
  pontos : Ponto[] = [];

  constructor(private pontoService: PontoService) {
    this.pontoService
                .listarPontoPorPeriodo(this.ano, this.mes)
                .subscribe( res => this.pontos = res );
   }

  ngOnInit() {

  }

  listarPontoPorPeriodo() {
    this.pontoService
                .listarPontoPorPeriodo(this.mes, this.ano)
                .subscribe( res => this.pontos = res );
  }

}
