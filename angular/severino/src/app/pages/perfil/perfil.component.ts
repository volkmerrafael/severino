import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../shared/models/usuario';
import { Login } from "../../shared/models/login";
import { Acesso } from './../../shared/models/acesso';
import { Departamento } from '../../shared/models/departamento';
import { Funcao } from '../../shared/models/funcao';
import { Location } from '@angular/common';
import { RouterLink } from '@angular/router/src/directives/router_link';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  usuario: Usuario = new Usuario();
  acesso: Acesso = new Acesso();
  departamento: Departamento = new Departamento();
  funcao: Funcao = new Funcao;

  constructor(
    private location: Location,
  ) {   }

  ngOnInit() {
    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.acesso.nomeacesso = sessionStorage.getItem('nomeacesso');
    this.usuario.email = sessionStorage.getItem('emailUsuario');
    this.usuario.pis = sessionStorage.getItem('pisUsuario');
    this.departamento.nome = sessionStorage.getItem('departamentoUsuario');
    this.funcao.nome = sessionStorage.getItem('funcaoUsuario');
    this.usuario.data_admissao = sessionStorage.getItem('dataAdmissao');
  }

  voltar() {
    this.location.back();
  }

}
