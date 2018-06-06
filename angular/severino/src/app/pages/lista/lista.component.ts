import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})
export class ListaComponent implements OnInit {

  usuarios: any;
  cols: any[];
  idUsuario: string;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.usuarioService.listaUsuarios()
    .subscribe( res => {
      this.usuarios = res;
      this.cols = [
        { field: 'nome', header: 'Nome' },
        { field: 'acesso.email', header: 'E-mail' }
      ];
    });
  }

  consultarUsuario(id: any) {
    this.router.navigate(['/perfil'], {queryParams: {id}});
    console.log(id);
  }


}
