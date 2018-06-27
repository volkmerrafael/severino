import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../shared/models/usuario';
import { Acesso } from './../../shared/models/acesso';
import { Departamento } from '../../shared/models/departamento';
import { Funcao } from '../../shared/models/funcao';
import { Location } from '@angular/common';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';
import * as moment from 'moment/moment';

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
  idUsuario: number;
  senha: string;
  tipo: string;
  mensagemGrow;
  tituloGrow;
  tipoGrow;

  constructor(
    private location: Location,
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
  ) {
    this.tipo = sessionStorage.getItem('tipo');
    this.idUsuario = parseInt(sessionStorage.getItem('id'), 10);
  }

  ngOnInit() {
    this.listaId = this.route.queryParams;
    this.idAny = this.listaId.value;
    this.id = parseInt((this.idAny.id), 10);
    this.perfil();
  }

  perfil() {
    this.usuarioService.usuario(this.id)
    .subscribe(res => {
      this.usuario = res;
      this.usuario.data_admissao = moment(this.usuario.data_admissao).format('DD/MM/YYYY');
      this.acesso = this.usuario.acesso;
      this.departamento = this.usuario.departamento;
      this.funcao = this.usuario.funcao;
      this.senha = "";
    }, error => {
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  voltar() {
    this.location.back();
  }

  editarUsuario(id: any) {
    this.router.navigate(['/editar-perfil'], {queryParams: {id}});
  }

  pontoUsuario(id: any) {
    this.router.navigate(['/ponto'], {queryParams: {id}});
  }
}
