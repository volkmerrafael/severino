import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { RouterLink } from '@angular/router/src/directives/router_link';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Usuario } from '../../shared/models/usuario';
import { Acesso } from '../../shared/models/acesso';
import { Login } from '../../shared/models/login';
import { UsuarioService } from '../../services/usuario.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-editar-perfil',
  templateUrl: './editar-perfil.component.html',
  styleUrls: ['./editar-perfil.component.css']
})
export class EditarPerfilComponent implements OnInit {

  perfilForm: FormGroup;
  usuario: Usuario = new Usuario();
  acesso: Acesso = new Acesso();
  senha: string;
  myGroup: FormGroup;
  login: Login;
  id: number;
  mensagemGrow;
  tituloGrow;
  tipoGrow;
  listaId: any;
  idAny: any;
  admin: boolean;

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.admin = false;
    this.listaId = this.route.queryParams;
    this.idAny = this.listaId.value;
    this.id = parseInt((this.idAny.id), 10);
    this.buscaPerfil();
    this.perfilForm = this.fb.group({
      'inputNome': new FormControl('', [Validators.required]),
      'inputNomeAcesso': new FormControl('', [Validators.required]),
      'inputEmail': new FormControl('', [Validators.required]),
      'inputSenha': new FormControl('', [Validators.required]),
    });
    if (sessionStorage.getItem('tipo') === 'ADMIN') {
      this.admin = true;
    }
  }

  buscaPerfil() {
    this.usuarioService.usuario(this.id)
      .subscribe(res => {
        this.usuario = res;
        this.acesso = this.usuario.acesso;
        this.senha = "";
      }, error => {
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      });
  }

  clickEditar() {
    this.acesso.senha = this.senha;
    this.usuario.acesso = this.acesso;
    this.usuarioService.editar(this.usuario)
      .subscribe(res => {
        if (this.admin === false) {
          sessionStorage.setItem('id', '' + res.id);
          sessionStorage.setItem('nomeUsuario', res.nome);
          sessionStorage.setItem('emailUsuario', res.email);
          this.acesso = res.acesso;
          sessionStorage.setItem('usertoken', this.acesso.token);
          sessionStorage.setItem('nomeacesso', this.acesso.nomeacesso);
        }
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
        this.mensagemGrow = "Perfil atualizado";
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
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

}
