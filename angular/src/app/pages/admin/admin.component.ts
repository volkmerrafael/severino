import { Component, OnInit } from "@angular/core";
import { Usuario } from "../../model/usuario";
import { UsuarioService } from "../../services/usuario.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  usuario: Usuario;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.usuarioService.obterUsuario().subscribe(res => this.usuario = res);
  }

}
