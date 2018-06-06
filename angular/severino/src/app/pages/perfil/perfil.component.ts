import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../shared/models/usuario';
import { Login } from "../../shared/models/login";
import { Acesso } from './../../shared/models/acesso';
import { Departamento } from '../../shared/models/departamento';
import { Funcao } from '../../shared/models/funcao';
import { Location } from '@angular/common';
import { RouterLink } from '@angular/router/src/directives/router_link';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute } from '@angular/router';

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
  id: number;
  senha: string;
  admin: boolean;

  constructor(
    private location: Location,
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
  ) {   }

  ngOnInit() {
    this.admin = false;
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
    this.perfil();
    if (sessionStorage.getItem('tipo') === 'ADMIN') {
      this.admin = true;
    }
  }

  perfil() {
  this.usuarioService.usuario(this.usuario.id)
    .subscribe( res => {
      this.usuario = res;
      this.acesso = this.usuario.acesso;
      this.departamento = this.usuario.departamento;
      this.funcao = this.usuario.funcao;
      this.senha = "";
    });
  }

  perfilColaborador() {
    this.usuarioService.usuario(this.id)
    .subscribe(res => {
      this.usuario = res;
      this.acesso = this.usuario.acesso;
      this.departamento = this.usuario.departamento;
      this.funcao = this.usuario.funcao;
      this.senha = "";
      console.log(res);
    });
  }

  voltar() {
    this.location.back();
  }

}
