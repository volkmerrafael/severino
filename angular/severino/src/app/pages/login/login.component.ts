import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioService } from "../../services/usuario.service";
import { Login } from "../../shared/model/login";

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

      if (res.usuario.acesso.tipo === "ADMIN") {
        this.router.navigate(['/admin']);
      } else {
        sessionStorage.setItem('departamentoUsuario', res.usuario.departamento.nome);
        sessionStorage.setItem('pisUsuario', res.usuario.pis);
        sessionStorage.setItem('funcaoUsuario', res.usuario.funcao.nome);
        sessionStorage.setItem('dataAdmissao', res.usuario.data_admissao);
        this.router.navigate(['/ponto']);
      }
    });

  }

}
