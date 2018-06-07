import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../shared/models/usuario';
import { Login } from "../../shared/models/login";
import { Acesso } from './../../shared/models/acesso';
import { Departamento } from '../../shared/models/departamento';
import { Funcao } from '../../shared/models/funcao';
import { Location } from '@angular/common';
import { RouterLink } from '@angular/router/src/directives/router_link';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';

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
  listaId: any;
  idAny: any;
  id: number;
  senha: string;
  admin: boolean;

  constructor(
    private location: Location,
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit() {
    this.listaId = this.route.queryParams;
    this.idAny = this.listaId.value;
    this.id = parseInt((this.idAny.id), 10);
    this.perfil();
    this.admin = false;
    if (sessionStorage.getItem('tipo') === 'ADMIN') {
      this.admin = true;
    }
  }

  perfil() {
    this.usuarioService.usuario(this.id)
    .subscribe(res => {
      this.usuario = res;
      this.acesso = this.usuario.acesso;
      this.departamento = this.usuario.departamento;
      this.funcao = this.usuario.funcao;
      this.senha = "";
    });
  }

  voltar() {
    this.location.back();
  }

  editarUsuario(id: any) {
    this.router.navigate(['/editar-perfil'], {queryParams: {id}});
  }
}
