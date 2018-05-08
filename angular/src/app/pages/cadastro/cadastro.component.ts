import { Component } from "@angular/core";
import { Usuario } from "../../model/usuario";
import { UsuarioService } from "../../services/usuario.service";
import { UsuarioAcesso } from "../../model/usuario-acesso";
import { Router } from "@angular/router";

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent {

  usuario: Usuario;

  constructor(private usuarioService: UsuarioService,
              private router: Router) {
    this.usuario = new Usuario();
    this.usuario.nomeacesso = localStorage.getItem('nomeacesso');
  }

  cadastrar() {
    this.usuarioService.cadastro( this.usuario ).subscribe(res => {
      const ua = <UsuarioAcesso>{};
      ua.username = this.usuario.nomeacesso;
      ua.password = this.usuario.senha;

      this.usuarioService.login( ua ).subscribe(token => {
        localStorage.setItem('token', token);
        this.router.navigate(['/admin']);
      });
    });
  }

}
