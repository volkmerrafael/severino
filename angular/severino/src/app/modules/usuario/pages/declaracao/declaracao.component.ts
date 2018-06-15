import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import * as jsPDF from 'jspdf';

import { JustificativaService } from '../../../../services/justificativa.service';
import { Usuario } from '../../../../shared/models/usuario';

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

  constructor(
    @Inject('Window') private window: Window,
    private justificativaService: JustificativaService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
    this.params = this.route.queryParams;
    this.dadosRota = this.params.value;
    this.justificativaService.listaJustificativasNoMes(this.dadosRota.id, this.dadosRota.ano, this.dadosRota.mes)
      .subscribe(res => {
        res.forEach(dado => {
          const justificativaTxt = "<ul><li>" + dado.descricao + "</li></ul>";
          this.txtEditor = this.txtEditor + justificativaTxt + "<br>";
          this.form.controls.editor.setValue(this.txtEditor);
        });
        this.declaracao = res;
      });
  }

  download(element: string) {
    console.log(element);
    const pdf = new jsPDF('p', 'pt', 'a4');
    pdf.addHTML(element, () => {
      this.doc.save('web.pdf');
    });
  }

  download01(element: any) {
    const canvas = document.getElementById("canvas");
    const html = element;
    // Save the PDF
    this.doc.save('Teste.pdf');
  }

  download02() {
    this.doc.text(20, 30, this.txtEditor);
    // Save the PDF
    this.doc.save('Test.pdf');
  }

}
