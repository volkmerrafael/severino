import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioService } from "../../services/usuario.service";
import { Login } from "../../model/login";
import { Usuario } from "../../model/usuario";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  usuario: Login;

  constructor(private usuarioService: UsuarioService,
    private router: Router) {

  this.usuario = <Login>{};

   
}

  login() {
    this.usuarioService.login(this.usuario).subscribe(res => {

      sessionStorage.setItem('nomeUsuario', res.usuario.nome);

      sessionStorage.setItem('usertoken', res.usertoken);
      sessionStorage.setItem('sessaotoken', res.sessaotoken);
      sessionStorage.setItem('nomeacesso', res.nomeacesso);

      this.router.navigate(['/ponto']);
    });

  }

}
