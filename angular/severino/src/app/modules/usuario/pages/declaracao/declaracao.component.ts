import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as jsPDF from 'jspdf';

@Component({
  selector: 'app-declaracao',
  templateUrl: './declaracao.component.html',
  styleUrls: ['./declaracao.component.css'],
  providers: [
    { provide: 'Window',  useValue: window }
  ]
})
export class DeclaracaoComponent implements OnInit {

  doc: jsPDF = new jsPDF();
  form = new FormGroup ({
    editor: new FormControl()
  });
  display: Boolean = true;

  constructor(
    @Inject('Window') private window: Window,
  ) { }

  ngOnInit() {
  }

 /* download(element: string) {
    const pdf = new jsPDF('p', 'pt', 'a4');
    pdf.addHTML(element, () => {
        this.doc.save('web.pdf');
    });
  } */

  download(element: string) {
    console.log(element);
    // Save the PDF
    this.doc.save('Test.pdf');
  }

}
