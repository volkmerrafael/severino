import { Component, OnInit, Inject, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as jsPDF from 'jspdf';
import { Usuario } from '../../../../shared/models/usuario';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PontoService } from '../../../../services/ponto.service';
import { Justificativa } from '../../../../shared/models/justificativa';
import { FormatarMinutosPipe } from '../../../../shared/components/pipes/time.pipe';
import { FormatarDataPipe } from '../../../../components/pipes/pipe';
import { Issues } from '../../../../shared/models/issues';


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
  worklogs: Issues[] = [];
  issue: string;
  listaIssues: string[] = [];
  horarioAlterado: string;
  myDate: Date = new Date();
  titulo: string;
  descricao: string;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private pontoService: PontoService,
    private formatarMinutosPipe: FormatarMinutosPipe,
    private formatarDataPipe: FormatarDataPipe
  ) { }

  ngOnInit() {
    this.cabecalho1 = "Eu <strong>" + sessionStorage.getItem('nomeUsuario') + "</strong> portador(a) do número PIS: <strong>"
    + sessionStorage.getItem('pisUsuario') + "</strong> nos termos do banco de horas vigente, declaro que realizei atividades"
    + " pertinentes ao meu cargo nos dias e horários a seguir: <br><br>";
    this.cabecalho2 = "Eu <strong>" + sessionStorage.getItem('nomeUsuario') + "</strong> portador(a) do número PIS: <strong>"
    + sessionStorage.getItem('pisUsuario') + "</strong> nos termos do banco de horas vigente, declaro que realizei compensação"
    + " de horas nos dias e horários a seguir: <br><br>";
    this.cabecalho3 = "Eu <strong>" + sessionStorage.getItem('nomeUsuario') + "</strong> portador(a) do número PIS: <strong>"
      + sessionStorage.getItem('pisUsuario') + "</strong> solicito que seja feito o ajuste do ponto nos dias e horários a seguir: <br><br>";
    this.rodape = "<br><br><p style='text-align:center'><strong>__________________________________________________</strong></p>"
      + "<p style='text-align:center'><strong>Assinatura do responsável pela empresa ou departamento do funcionário</strong></p><br><br>"
      + "<p style='text-align:center'><strong>__________________________________________________</strong></p>"
      + "<p style='text-align:center'><strong>Assinatura do funcionário</strong></p><br>"
      + "<p style='text-align:center'><strong>Impresso dia: " + this.myDate.toLocaleDateString() + "</strong></p>";
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
          this.descricao = '';
          if (ponto.justificativa) {
            this.validaJust = ponto.justificativa;
          }
            if (ponto.issues) {
              this.worklogs = ponto.issues;
            }
            this.issue = '';
            this.worklogs.forEach(item => {
              if (item.id) {
                this.issue = this.issue + " - " + item.issue + " - " + item.summary;
              }
            });
            if (ponto.status === 'CREDITO') {
              if (this.validaJust.descricao) {
                this.validaJust.descricao = this.formatarDataPipe.transform(ponto.data) + " - "
              + this.formatarMinutosPipe.transform(ponto.minutos_credito) + this.issue + "<br>" + "-- " + this.validaJust.descricao
              + "<br>";
              this.justificativasCredito.push(this.validaJust);
              } else if (this.issue) {
                this.validaJust.descricao = this.formatarDataPipe.transform(ponto.data) + " - "
              + this.formatarMinutosPipe.transform(ponto.minutos_credito) + this.issue + "<br>";
              this.justificativasCredito.push(this.validaJust);
              }
            }
            if (ponto.status === 'DEBITO') {
              if (this.validaJust.descricao) {
                this.descricao = "<br>" + "-- " + this.validaJust.descricao;
              }
              this.validaJust.descricao = this.formatarDataPipe.transform(ponto.data) + " - "
              + this.formatarMinutosPipe.transform(ponto.minutos_debito) + this.issue + this.descricao + "<br>";
              this.justificativasDebito.push(this.validaJust);
            }
            if (ponto.status === 'MARCACAO_INCORRETA') {
              this.validaJust.descricao = this.formatarDataPipe.transform(ponto.data) + ' ' + this.validaJust.descricao;
              this.justificativasMarcInc.push(this.validaJust);
            }
        });
        this.justificativaTxt = "";
        if (this.dadosRota.tipo === 'ext') {
          this.titulo = "<strong>DECLARAÇÃO DE HORAS EXTRAS</strong>";
          this.justificativasCredito.forEach(dado => {
            this.justificativaTxt = "<li>" + dado.descricao + "</li>";
            this.txtEditor = this.txtEditor + this.justificativaTxt;
            this.form.controls.editor.setValue(this.cabecalho1 + this.txtEditor + this.rodape);
          });
        } else if (this.dadosRota.tipo === 'comp') {
          this.titulo = "<strong>DECLARAÇÃO DE COMPENSAÇÃO HORAS</strong>";
          this.justificativasDebito.forEach(dado => {
            this.justificativaTxt = "<li>" + dado.descricao + "</li>" + "<br>";
            this.txtEditor = this.txtEditor + this.justificativaTxt + "<br>";
            this.form.controls.editor.setValue(this.cabecalho2 + this.txtEditor + this.rodape);
          });
        } else {
          this.justificativasMarcInc.forEach(dado => {
            this.titulo = "<strong>DECLARAÇÃO DE JUSTIFICATIVA PONTO</strong>";
            this.justificativaTxt = "<li>" + dado.descricao + "</li>" + "<br>";
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
    doc.addFont('ArialMS', 'Arial', 'normal');
    doc.setFont('Arial');
    doc.getFontList();
    doc.fromHTML(this.titulo, 60, 10, {
      'width': 100,
    });
    doc.fromHTML(this.form.controls.editor.value, 15, 15, {
      'width': 190,
    });
    doc.save('Declaração.pdf');
  }

}
