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
      console.log("Componente: login.component.ts Método: login()");      
      console.log(res.usertoken);
      console.log(res.nomeacesso);
      console.log(res.sessaotoken);
      sessionStorage.setItem('usertoken', res.usertoken);
      sessionStorage.setItem('sessaotoken', res.sessaotoken);
      sessionStorage.setItem('nomeacesso', res.nomeacesso);

      this.router.navigate(['/ponto']);
    });

  }

}
