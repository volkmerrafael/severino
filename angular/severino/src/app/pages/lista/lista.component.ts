import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})
export class ListaComponent implements OnInit {

  usuarios: any;
  cols: any[];
  idUsuario: string;
  mensagemGrow;
  tituloGrow;
  tipoGrow;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private messageService: MessageService,
  ) { }

  ngOnInit() {
    this.usuarioService.listaUsuarios()
    .subscribe( res => {
      this.usuarios = res;
      this.cols = [
        { field: 'nome', header: 'Nome' },
        { field: 'acesso.email', header: 'E-mail' }
      ];
    }, error => {
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  consultarUsuario(id: any) {
    this.router.navigate(['/perfil'], {queryParams: {id}});
  }


}
