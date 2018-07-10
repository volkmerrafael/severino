import { Component, OnInit } from '@angular/core';
import { ConfiguracaoIntegracao } from '../../../../shared/models/configuracaoIntegracao';
import { Usuario1Service } from '../../usuario.service';

@Component({
  selector: 'app-configuracao',
  templateUrl: './configuracao.component.html',
  styleUrls: ['./configuracao.component.css']
})
export class ConfiguracaoComponent implements OnInit {

  configuracao: ConfiguracaoIntegracao = new ConfiguracaoIntegracao;
  checkedJira: boolean;
  checkedSlack: boolean;

  constructor(
    private usuario1Service: Usuario1Service,
  ) {
    this.buscaConfiguracao(); }

  ngOnInit() {
  }
  buscaConfiguracao() {
    this.usuario1Service.getConfiguracao()
    .subscribe(res => {
      res.forEach( item => {
        this.configuracao = item;
      });
    });
  }

  alteraConfiguracao() {
    this.usuario1Service.putConfiguracao(this.configuracao)
    .subscribe( res => {
    });
  }

}
