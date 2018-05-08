import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioService } from "../../services/usuario.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  nomeacesso: string;

  constructor(private router: Router,
              private usuarioService: UsuarioService) {}

  onSubmit() {
    localStorage.setItem('nomeacesso', this.nomeacesso);

    this.usuarioService.existeNomeAcesso( this.nomeacesso ).subscribe( res => {
      if ( res ) {
        this.router.navigate(['/senha']);
      } else {
        this.router.navigate(['/cadastro']);
      }
    });
  }

}
