import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioService } from "../../services/usuario.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email: string;

  constructor(private router: Router,
              private usuarioService: UsuarioService) {}

  onSubmit() {
    localStorage.setItem('email', this.email);

    this.usuarioService.existeEmail( this.email ).subscribe( res => {
      if ( res ) {
        this.router.navigate(['/senha']);
      } else {
        this.router.navigate(['/cadastro']);
      }
    });
  }

}
