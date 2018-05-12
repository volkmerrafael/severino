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
      console.log('usertoken', res.usertoken);
      console.log('sessaotoken', res.sessaotoken);
      localStorage.setItem('usertoken', res.usertoken);
      localStorage.setItem('sessaotoken', res.sessaotoken);
      console.log(res);
      if (res.sessaotoken != null) {
        this.router.navigate(['/painel-geral']);
      }
    });

  }

}
