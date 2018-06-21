import { Component, OnInit, Inject, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as jsPDF from 'jspdf';
import { JustificativaService } from '../../../../services/justificativa.service';
import { Usuario } from '../../../../shared/models/usuario';
import { ActivatedRoute } from '@angular/router';
import { WorklogJiraService } from '../../../../services/worklogJira.service';
import { Location } from '@angular/common';
import { PontoService } from '../../../../services/ponto.service';
import { Justificativa } from '../../../../shared/models/justificativa';
import { Issue } from '../../../../shared/models/issue';
import { FormatarMinutosPipe } from '../../../../shared/components/pipes/time.pipe';
import { FormatarDataPipe } from '../../../../components/pipes/pipe';


@Component({
  selector: 'app-declaracao',
  templateUrl: './declaracao.component.html',
  styleUrls: ['./declaracao.component.css'],
  providers: [FormatarDataPipe, FormatarMinutosPipe,
    { provide: 'Window', useValue: window }
  ]
})
export class DeclaracaoComponent implements OnInit {


  doc: jsPDF = new jsPDF();
  txtEditor: String = '';
  form = new FormGroup({
    editor: new FormControl()
  });
  usuario: Usuario = new Usuario;
  params: any;
  dadosRota: any;
  declaracao: any;
  cabecalho1: string;
  cabecalho2: string;
  cabecalho3: string;
  rodape: string;
  justificativaTxt: string;
  validaJust: Justificativa = new Justificativa();
  justificativasDebito: Justificativa[] = [];
  justificativasCredito: Justificativa[] = [];
  justificativasMarcInc: Justificativa[] = [];
  worklogs: Issue[] = [];
  issue: string;
  listaIssues: string[] = [];
  horarioAlterado: string;

  constructor(
    private justificativaService: JustificativaService,
    private worklogJiraService: WorklogJiraService,
    private route: ActivatedRoute,
    private location: Location,
    private pontoService: PontoService,
    private formatarMinutosPipe: FormatarMinutosPipe,
    private formatarDataPipe: FormatarDataPipe
  ) { }

  ngOnInit() {
    this.cabecalho1 = "<p style='text-align:center'>DECLARAÇÃO DE HORAS EXTRAS</p><p style='text-align:center'>Eu "
      + sessionStorage.getItem('nomeUsuario') + " portador(a) do número PIS: " + sessionStorage.getItem('pisUsuario')
      + " nos termos do banco de horas vigente, declaro que realizei atividades pertinentes ao meu cargo nos dias e horarios a seguir: </p>"
      + "<p style='text-align:center'><br></p>";
    this.cabecalho2 = "<p style='text-align:center'>DECLARAÇÃO DE COMPENSAÇÃO HORAS</p><p style='text-align:center'>Eu "
      + sessionStorage.getItem('nomeUsuario') + " portador(a) do número PIS: " + sessionStorage.getItem('pisUsuario')
      + " nos termos do banco de horas vigente, declaro que realizei compensação de horas nos dias e horarios a seguir : </p>"
      + "<p style='text-align:center'><br></p>";
    this.cabecalho3 = "<p style='text-align:center'>DECLARAÇÃO DE JUSTIFICATIVA PONTO</p><p style='text-align:center'>Eu "
      + sessionStorage.getItem('nomeUsuario') + " portador(a) do número PIS: " + sessionStorage.getItem('pisUsuario')
      + " solicito que seja feito o ajuste do ponto, pois não registrei pelos motivos de: </p>"
      + "<p style='text-align:center'><br></p>";
    this.rodape = "<p style='text-align:center'><strong>________________________________________________</strong></p>"
      + "<p style='text-align:center'><br></p><p style='text-align:center'>"
      + "<strong>Assinatura do responsável pela empresa ou departamento do funcionário</strong></p><p style='text-align:center'>"
      + "<strong>&nbsp;</strong></p><p style='text-align:center'><strong>&nbsp;</strong></p><p style='text-align:center'>"
      + "<strong>&nbsp;</strong></p><p style='text-align:center'><strong>_________________________________________________</strong>"
      + "</p><p style='text-align:center'><strong>&nbsp;</strong></p><p style='text-align:center'><strong>Assinatura do funcionário"
      + "</strong></p><p style='text-align:center'><br></p><p style='text-align:center'><br></p><p style='text-align:center'>"
      + "<strong>Entregue dia: </strong></p><p><br></p>";
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
    this.params = this.route.queryParams;
    this.dadosRota = this.params.value;
    this.justificativasPorStatus();
  }

  justificativasPorStatus() {
    this.pontoService.listarPontoPorPeriodo(this.usuario.id, this.dadosRota.ano, this.dadosRota.mes)
      .subscribe(res => {
        this.validaJust = new Justificativa();
        res.forEach(ponto => {
          if (ponto.justificativa) {
            this.validaJust = ponto.justificativa;
            if (ponto.worklogs) {
              this.worklogs = ponto.worklogs;
            }
            this.issue = '';
            this.worklogs.forEach(item => {
              if (item.gravada === true) {
                this.issue = this.issue + " - " + item.issue + " - " + item.summary + " (Iniciado as: "
                  + this.formatarMinutosPipe.buscar(item.startdate, 11, 16)
                  + " Tempo trabalhado: " + this.formatarMinutosPipe.horaTransform(item.timeworked) + ") <br>";
              }
            });
            if (ponto.status === 'CREDITO') {
              this.validaJust.descricao = this.formatarDataPipe.transform(ponto.data) + " - " + this.validaJust.descricao + "<br>"
                + this.issue;
              this.justificativasCredito.push(this.validaJust);
            }
            if (ponto.status === 'DEBITO') {
              this.validaJust.descricao = this.formatarDataPipe.transform(ponto.data) + " - " + ponto.observacao
              + " - " + this.validaJust.descricao + "<br>";
              this.justificativasDebito.push(this.validaJust);
            }
            if (ponto.status === 'MARCACAO_INCORRETA') {
              this.horarioAlterado = "";
              if (ponto.entrada1.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.entrada1 + this.horarioAlterado;
              }
              if (ponto.entrada2.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.entrada2 + this.horarioAlterado;
              }
              if (ponto.entrada3.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.entrada3 + this.horarioAlterado;
              }
              if (ponto.entrada4.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.entrada4 + this.horarioAlterado;
              }
              if (ponto.saida1.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.saida1 + this.horarioAlterado;
              }
              if (ponto.saida2.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.saida2 + this.horarioAlterado;
              }
              if (ponto.saida3.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.saida3 + this.horarioAlterado;
              }
              if (ponto.saida4.indexOf("*") !== -1) {
                this.horarioAlterado = " - " + ponto.saida4 + this.horarioAlterado;
              }
              this.validaJust.descricao = this.formatarDataPipe.transform(ponto.data) + this.horarioAlterado;
              this.justificativasMarcInc.push(this.validaJust);
            }
          }
        });
        this.justificativaTxt = "";
        if (this.dadosRota.tipo === 'ext') {
          this.justificativasCredito.forEach(dado => {
            this.justificativaTxt = "<ul><li>" + dado.descricao + "</li></ul>" + "<br>";
            this.txtEditor = this.txtEditor + this.justificativaTxt + "<br>";
            this.form.controls.editor.setValue(this.cabecalho1 + this.txtEditor + this.rodape);
          });
        } else if (this.dadosRota.tipo === 'comp') {
          this.justificativasDebito.forEach(dado => {
            this.justificativaTxt = "<ul><li>" + dado.descricao + "</li></ul>" + "<br>";
            this.txtEditor = this.txtEditor + this.justificativaTxt + "<br>";
            this.form.controls.editor.setValue(this.cabecalho2 + this.txtEditor + this.rodape);
          });
        } else {
          this.justificativasMarcInc.forEach(dado => {
            this.justificativaTxt = "<ul><li>" + dado.descricao + "</li></ul>" + "<br>";
            this.txtEditor = this.txtEditor + this.justificativaTxt + "<br>";
            this.form.controls.editor.setValue(this.cabecalho3 + this.txtEditor + this.rodape);
          });
        }
      });
  }

  voltar() {
    this.location.back();
  }

  public dowloadPDF() {
    const doc = new jsPDF();
    doc.fromHTML(this.form.controls.editor.value, 15, 15, {
      'width': 190,
    });
    doc.save('test.pdf');
  }

}
