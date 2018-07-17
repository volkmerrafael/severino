import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';
import { Acesso } from '../../shared/models/acesso';

@Component({
  selector: 'app-lista-tipo-evento',
  templateUrl: './listatipoevento.component.html',
  styleUrls: ['./listatipoevento.component.css']
})
export class ListaTipoEventoComponent implements OnInit {

  tiposevento: any;
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
      this.listaTipoEventos();
  }

  listaTipoEventos() {
    this.usuarioService.tiposevento()
    .subscribe( res => {
      this.tiposevento = res;
      this.cols = [
        { field: 'id', header: 'Código' },
        { field: 'descricao', header: 'Descrição' },
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

  consultarTipoEvento(id: any) {
    this.router.navigate(['/perfil'], {queryParams: {id}});
  }

  cadastrarTipoEvento(id: any) {
    this.router.navigate(['/editar-tipo-evento'], {queryParams: {id}});
  }


}
