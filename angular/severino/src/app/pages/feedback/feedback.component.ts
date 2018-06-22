import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {


  mensagemGrow: any;
  tituloGrow: any;
  tipoGrow: any;
  texto: string;

  constructor(
    private location: Location,
    private messageService: MessageService,
  ) { }

  ngOnInit() {
  }

  voltar() {
    this.location.back();
  }

  salvar() {
    this.tipoGrow = "success";
    this.tituloGrow = "";
    this.mensagemGrow = "Feedback";
    this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

}
