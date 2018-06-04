import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { RouterLink } from '@angular/router/src/directives/router_link';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Usuario } from '../../shared/models/usuario';
import { Acesso } from '../../shared/models/acesso';
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

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
  ) { }

  ngOnInit() {

    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.acesso.nomeacesso = sessionStorage.getItem('nomeacesso');
    this.usuario.email = sessionStorage.getItem('emailUsuario');

    this.perfilForm = this.fb.group({
      'inputNome': new FormControl('', [Validators.required]),
      'inputNomeAcesso': new FormControl(''),
      'inputEmail': new FormControl('', [Validators.required]),
      'inputSenha': new FormControl('', [Validators.required]),
    });
  }


  voltar() {
    this.location.back();
  }

}
