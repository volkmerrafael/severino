import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';
import { Acesso } from '../../shared/models/acesso';

@Component({
  selector: 'app-lista-funcao',
  templateUrl: './listafuncao.component.html',
  styleUrls: ['./listafuncao.component.css']
})
export class ListaFuncaoComponent implements OnInit {

  funcoes: any;
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
      this.listaFuncoes();
  }

  listaFuncoes() {
    this.usuarioService.funcoes()
    .subscribe( res => {
      this.funcoes = res;
      this.cols = [
        { field: 'id', header: 'Código' },
        { field: 'nome', header: 'Nome' },
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

  cadastrarTipoEvento(id: any) {
    this.router.navigate(['/editar-funcoes'], {queryParams: {id}});
  }


}
