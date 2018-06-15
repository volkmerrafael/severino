import { Component, OnInit, Inject, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as jsPDF from 'jspdf';
import { JustificativaService } from '../../../../services/justificativa.service';
import { Usuario } from '../../../../shared/models/usuario';
import { ActivatedRoute } from '@angular/router';


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

  public dowloadPDF() {
    const doc = new jsPDF();
    doc.fromHTML(this.form.controls.editor.value, 15, 15, {
    'width': 190,
    });
    doc.save('test.pdf');
    }

}
