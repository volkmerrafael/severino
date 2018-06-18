import { Component, OnInit, Inject, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as jsPDF from 'jspdf';
import { JustificativaService } from '../../../../services/justificativa.service';
import { Usuario } from '../../../../shared/models/usuario';
import { ActivatedRoute } from '@angular/router';
import { WorklogJiraService } from '../../../../services/worklogJira.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-declaracao',
  templateUrl: './declaracao.component.html',
  styleUrls: ['./declaracao.component.css'],
  providers: [
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

  constructor(
    private justificativaService: JustificativaService,
    private worklogJiraService: WorklogJiraService,
    private route: ActivatedRoute,
    private location: Location,
  ) { }

  ngOnInit() {

    this.cabecalho1 = "<p style='text-align:center'>DECLARAÇÃO DE HORAS EXTRAS</p><p style='text-align:center'>Eu " + sessionStorage.getItem('nomeUsuario') + " portador(a) do número PIS: " + sessionStorage.getItem('pisUsuario') + " nos termos do banco de horas vigente, declaro que realizei atividades pertinentes ao meu cargo nos dias e horarios a seguir: </p><p style='text-align:center'><br></p>"
    this.cabecalho2 = "<p style='text-align:center'>DECLARAÇÃO DE COMPENSAÇÃO HORAS</p><p style='text-align:center'>Eu " + sessionStorage.getItem('nomeUsuario') + " portador(a) do número PIS: " + sessionStorage.getItem('pisUsuario') + " nos termos do banco de horas vigente, declaro que realizei compensação de horas nos dias e horarios a seguir: </p><p style='text-align:center'><br></p>"
    this.cabecalho3 = "<p style='text-align:center'>DECLARAÇÃO DE JUSTIFICATIVA PONTO</p><p style='text-align:center'>Eu " + sessionStorage.getItem('nomeUsuario') + " portador(a) do número PIS: " + sessionStorage.getItem('pisUsuario') + " solicito que seja feito o ajuste do ponto, pois não registrei nos dias e pelos motivos a seguir: </p><p style='text-align:center'><br></p>"
    this.rodape = "<p style='text-align:center'><strong>________________________________________________</strong></p><p style='text-align:center'><br></p><p style='text-align:center'><strong>Assinatura do responsável pela empresa ou departamento do funcionário</strong></p><p style='text-align:center'><strong>&nbsp;</strong></p><p style='text-align:center'><strong>&nbsp;</strong></p><p style='text-align:center'><strong>&nbsp;</strong></p><p style='text-align:center'><strong>_________________________________________________</strong></p><p style='text-align:center'><strong>&nbsp;</strong></p><p style='text-align:center'><strong>Assinatura do funcionário</strong></p><p style='text-align:center'><br></p><p style='text-align:center'><br></p><p style='text-align:center'><strong>Entregue dia: </strong></p><p><br></p>";
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
    this.params = this.route.queryParams;
    this.dadosRota = this.params.value;
    this.justificativaService.listaJustificativasNoMes(this.dadosRota.id, this.dadosRota.ano, this.dadosRota.mes)
      .subscribe(res => {
        res.forEach(dado => {
          const justificativaTxt = "<ul><li>" + dado.descricao + "</li></ul>";
          this.txtEditor = this.txtEditor + justificativaTxt + "<br>";
        });
        if (this.dadosRota.tipo === 'ext') {
        this.form.controls.editor.setValue(this.cabecalho1 + this.txtEditor + this.rodape);
        } else if (this.dadosRota.tipo === 'comp') {
        this.form.controls.editor.setValue(this.cabecalho2 + this.txtEditor + this.rodape);
        } else {
        this.form.controls.editor.setValue(this.cabecalho3 + this.txtEditor + this.rodape);
        }
        this.declaracao = res;
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
