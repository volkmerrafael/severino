import { Component, OnInit } from '@angular/core';
import { ConfiguracaoIntegracao } from '../../../../shared/models/configuracaoIntegracao';
import { Usuario1Service } from '../../usuario.service';
import { FormGroup, FormControl } from '@angular/forms';
import { UsuarioService } from '../../../../services/usuario.service';

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
    private usuarioService: UsuarioService,
  ) {
    this.buscaConfiguracao(); }

  ngOnInit() {
  }
  buscaConfiguracao() {
    this.usuario1Service.getConfiguracao()
    .subscribe(res => {
      res.forEach( item => {
        console.log(item);
        this.configuracao = item;
      });
      console.log(res);
    });
  }

  alteraConfiguracao() {
    this.usuario1Service.putConfiguracao(this.configuracao)
    .subscribe( res => {
      console.log(res);
    });
  }

}
