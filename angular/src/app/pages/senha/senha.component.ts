import { Component } from "@angular/core";
import { UsuarioService } from "../../services/usuario.service";
import { Login } from "../../model/login";
import { Router } from "@angular/router";

@Component({
  selector: 'app-senha',
  templateUrl: './senha.component.html',
  styleUrls: ['./senha.component.css']
})
export class SenhaComponent {

  usuario: Login;

  constructor(private usuarioService: UsuarioService,
              private router: Router) {
    this.usuario = <Login>{};
    this.usuario.nomeacesso = localStorage.getItem('nomeacesso');
  }

  /*this.usuarioService.cadastro( this.usuario ).subscribe(res => {
    const ua = <Login>{};
    ua.nomeacesso = this.usuario.nomeacesso;
    ua.senha = this.usuario.senha;

    this.usuarioService.login( ua ).subscribe(token => {
      localStorage.setItem('token', token);
      this.router.navigate(['/admin']);
    });
  //});
*/
  login() {
    this.usuarioService.login(this.usuario).subscribe(res => {
      console.log('usertoken', res.usertoken);
      console.log('sessaotoken', res.sessaotoken);
      localStorage.setItem('usertoken', res.usertoken);
      localStorage.setItem('sessaotoken', res.sessaotoken);
      this.router.navigate(['/admin']);
    });
  }

}
