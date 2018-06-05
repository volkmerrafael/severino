import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { RouterLink } from '@angular/router/src/directives/router_link';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Usuario } from '../../shared/models/usuario';
import { Acesso } from '../../shared/models/acesso';
import { Login } from '../../shared/models/login';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-editar-perfil',
  templateUrl: './editar-perfil.component.html',
  styleUrls: ['./editar-perfil.component.css']
})
export class EditarPerfilComponent implements OnInit {

  perfilForm: FormGroup;
  usuario: Usuario = new Usuario();
  acesso: Acesso = new Acesso();
  myGroup: FormGroup;
  login: Login;
  id: number;

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
  ) { }

  ngOnInit() {

    this.id = parseInt(sessionStorage.getItem('id'), 10);
    console.log(this.id);
    this.usuarioService.usuario(this.id)
    .subscribe( res => {
      this.usuario.nome = res.nome;
      this.usuario.email = res.email;
      console.log(res);
    });

    this.perfilForm = this.fb.group({
      'inputNome': new FormControl('', [Validators.required]),
      'inputNomeAcesso': new FormControl(''),
      'inputEmail': new FormControl('', [Validators.required]),
      'inputSenha': new FormControl('', [Validators.required]),
    });
  }

  clickEditar() {
    this.usuarioService.editar(this.usuario)
    .subscribe( res => {
    });
  }

  voltar() {
    this.location.back();
  }

}
