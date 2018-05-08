import { Component } from "@angular/core";
import { UsuarioService } from "../../services/usuario.service";
import { UsuarioAcesso } from "../../model/usuario-acesso";
import { Router } from "@angular/router";

@Component({
  selector: 'app-senha',
  templateUrl: './senha.component.html',
  styleUrls: ['./senha.component.css']
})
export class SenhaComponent {

  usuario: UsuarioAcesso;

  constructor(private usuarioService: UsuarioService,
              private router: Router) {
    this.usuario = <UsuarioAcesso>{};
    this.usuario.username = localStorage.getItem('nomeacesso');
  }

  login() {
    this.usuarioService.login(this.usuario).subscribe(res => {
      localStorage.setItem('token', res);
      this.router.navigate(['/admin']);
    });
  }

}
