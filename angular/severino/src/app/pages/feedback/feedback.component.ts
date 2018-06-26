import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { MessageService } from 'primeng/components/common/messageservice';
import { Feedback } from '../../shared/models/feedback';
import { FeedbackService } from '../../services/feedback.service';
import { Usuario } from '../../shared/models/usuario';
import { UsuarioService } from '../../services/usuario.service';
import * as moment from 'moment/moment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {

  feedback: Feedback = new Feedback();
  mensagemGrow: any;
  tituloGrow: any;
  tipoGrow: any;
  texto: string;
  filteredTipo: any[];
  tipos: string[] = ['Tarefa',  'Erro', 'Sugestão'];
  usuario: Usuario = new Usuario();
  tipoUsuario: any;
  cols: any[];
  feedbacks: any;
  usuariosFeedback: Usuario[] = [];

  constructor(
    private location: Location,
    private messageService: MessageService,
    private feedbackService: FeedbackService,
    private usuarioService: UsuarioService,
    private router: Router,
  ) {
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
    this.tipoUsuario = sessionStorage.getItem('tipo');
  }

  ngOnInit() {
    this.usuarioService.usuario(this.usuario.id)
    .subscribe( res => {
      this.usuario = res;
    });
    if (this.tipoUsuario === 'ADMIN') {
      this.feedbackService.listaFeedbacks()
      .subscribe( res => {
        this.feedbacks = res;
        this.feedbacks.forEach( item => {
          item.data_hora = moment(item.data_hora).format('DD/MM/YYYY');
          if (item.tipo === 'SUGESTAO') {
            item.tipo = 'Sugestão';
          } else if ( item.tipo === 'TAREFA') {
            item.tipo = 'Tarefa';
          } else if ( item.tipo === 'ERRO') {
            item.tipo = 'Erro';
          }
        });
        this.cols = [
          { field: 'nome', header: 'Nome' },
          { field: 'data', header: 'Data' },
          { field: 'tipo', header: 'Tipo' }
        ];
      });
    }
  }

  voltar() {
    this.location.back();
  }

  gravar() {
    this.feedback.id = undefined;
    this.feedback.usuario = this.usuario;
    if (this.feedback.tipo === 'Sugestão') {
      this.feedback.tipo = 'SUGESTAO';
    } else if ( this.feedback.tipo === 'Tarefa') {
      this.feedback.tipo = 'TAREFA';
    } else if ( this.feedback.tipo === 'Erro') {
      this.feedback.tipo = 'ERRO';
    }
    this.feedbackService.feedback(this.feedback)
    .subscribe( res => {
      this.tipoGrow = "success";
      this.tituloGrow = "";
      this.mensagemGrow = "Feedback";
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    }, error => {
      this.tipoGrow = "error";
      this.tituloGrow = "";
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  search(event) {
    this.filteredTipo = [];
    this.tipos.forEach( res => {
        const brand = res;
        if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
            this.filteredTipo.push(brand);
        }
    });
  }

  visualizarFeedback(id: any) {
    this.router.navigate(['/visualiza-feedback'], {queryParams: {id}});
  }

}
