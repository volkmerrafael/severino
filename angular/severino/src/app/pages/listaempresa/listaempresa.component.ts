import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';
import { Acesso } from '../../shared/models/acesso';

@Component({
  selector: 'app-lista-empresa',
  templateUrl: './listaempresa.component.html',
  styleUrls: ['./listaempresa.component.css']
})
export class ListaEmpresaComponent implements OnInit {

  empresas: any;
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
      this.listaDepartamentos();
  }

  listaDepartamentos() {
    this.usuarioService.empresas()
    .subscribe( res => {
      this.empresas = res;
      this.cols = [
        { field: 'id', header: 'Código' },
        { field: 'razao_social', header: 'Razão Social' },
        { field: 'cnpj', header: 'C.N.P.J.' },
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
    this.router.navigate(['/editar-empresa'], {queryParams: {id}});
  }


}
