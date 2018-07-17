import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';
import { Acesso } from '../../shared/models/acesso';

@Component({
  selector: 'app-lista-departamento',
  templateUrl: './listadepartamento.component.html',
  styleUrls: ['./listadepartamento.component.css']
})
export class ListaDepartamentoComponent implements OnInit {

  departamentos: any;
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
    this.usuarioService.departamentos()
    .subscribe( res => {
      this.departamentos = res;
      this.cols = [
        { field: 'id', header: 'Código' },
        { field: 'nome', header: 'Nome' },
        { field: 'tags_sistema_jira', header: 'Tags Sistema Jira' },
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
    this.router.navigate(['/editar-departamento'], {queryParams: {id}});
  }


}
