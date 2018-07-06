import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';
import { Acesso } from '../../shared/models/acesso';

@Component({
  selector: 'app-lista-evento',
  templateUrl: './listaevento.component.html',
  styleUrls: ['./listaevento.component.css']
})
export class ListaEventoComponent implements OnInit {

  eventos: any;
  cols: any[];
  mensagemGrow;
  tituloGrow;
  tipoGrow;
  tipo: any;
  acesso: Acesso[] = [];

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private messageService: MessageService,
  ) {
    this.tipo = sessionStorage.getItem('tipo');
  }

  ngOnInit() {
      this.listaEventos();
  }

  listaEventos() {
    this.usuarioService.eventos()
    .subscribe( res => {
      this.eventos = res;
      this.cols = [
        { field: 'id', header: 'Código' },
        { field: 'descricao', header: 'Descrição' },
        { field: 'data_hora_inicio', header: 'Início' },
        { field: 'data_hora_termino', header: 'Termino' },
        { field: 'limite_vagas', header: 'Lim. Vagas' },
        { field: 'confirmados', header: 'Confirmados' },
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

  consultarEvento(id: any) {
    this.router.navigate(['/perfil'], {queryParams: {id}});
  }

  cadastrarEvento(id: any) {
    this.router.navigate(['/editar-evento'], {queryParams: {id}});
  }

  participarEvento(id: any) {
    //chamar método evento/participar/{id}
    //this.usuarioService.evento
    //this.router.navigate(['/editar-evento'], {queryParams: {id}});
  }

}
