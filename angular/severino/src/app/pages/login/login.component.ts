import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioService } from "../../services/usuario.service";
import { Login } from "../../model/login";

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
      console.log(res.usertoken);
      console.log(res.nomeacesso);
      console.log(res.sessaotoken);
      localStorage.setItem('usertoken', res.usertoken);
      localStorage.setItem('sessaotoken', res.sessaotoken);
      localStorage.setItem('nomeacesso', res.nomeacesso);

      this.router.navigate(['/ponto']);
    });

  }

}
