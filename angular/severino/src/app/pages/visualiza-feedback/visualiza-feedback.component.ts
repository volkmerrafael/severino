import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FeedbackService } from '../../services/feedback.service';
import { Location } from '@angular/common';
import { Feedback } from '../../shared/models/feedback';
import { Usuario } from '../../shared/models/usuario';
import * as moment from 'moment/moment';

@Component({
  selector: 'app-visualiza-feedback',
  templateUrl: './visualiza-feedback.component.html',
  styleUrls: ['./visualiza-feedback.component.css']
})
export class VisualizaFeedbackComponent implements OnInit {

  listaId: any;
  idAny: any;
  id: number;
  feedback: Feedback = new Feedback();
  usuario: Usuario = new Usuario();

  constructor(
    private route: ActivatedRoute,
    private feedbackService: FeedbackService,
    private location: Location,
  ) { }

  ngOnInit() {
    this.listaId = this.route.queryParams;
    this.idAny = this.listaId.value;
    this.id = parseInt((this.idAny.id), 10);
    this.feedbackService.buscaFeedback(this.id)
    .subscribe( res => {
      this.feedback = res;
      this.feedback.data_hora = moment(this.feedback.data_hora).format('DD/MM/YYYY');
      this.usuario = this.feedback.usuario;
      if (res.tipo === 'SUGESTAO') {
          this.feedback.tipo = 'Sugestão';
      } else if ( res.tipo === 'TAREFA') {
        this.feedback.tipo = 'Tarefa';
      } else if ( res.tipo === 'ERRO') {
        this.feedback.tipo = 'Erro';
      }
      if (res.status === undefined) {
        this.feedback.status = ' ';
      }
    });
  }

  voltar() {
    this.location.back();
  }

}
