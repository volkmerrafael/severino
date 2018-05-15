import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../model/ponto';
import { ArquivoImportacao } from "../../model/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../model/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-ponto',
  templateUrl: './ponto.component.html',
  styleUrls: ['./ponto.component.css']
})
export class PontoComponent implements OnInit {

  mes : String = "05";
  ano : String = "2018";
  pontos : Ponto[] = [];
  importacao: Importacao = new Importacao(); 
  arqImportacao: ArquivoImportacao = new ArquivoImportacao();  

  constructor(private pontoService: PontoService) {
    this.pontoService
                .listarPontoPorPeriodo(this.ano, this.mes)
                .subscribe( res => this.pontos = res );           
   }

  ngOnInit() {

  }

  listarPontoPorPeriodo() {
    this.pontoService
                .listarPontoPorPeriodo(this.mes, this.ano)
                .subscribe( res => this.pontos = res );
  }

  onBasicUpload(event) {
    let arquivo = event.files[0];
    this.importacao.nome = arquivo.name;
    this.importacao.extensao = arquivo.name.substr(arquivo.name.lastIndexOf('.')+1);    
    let leitor = new FileReader();
    leitor.onload = this._handleReaderLoaded.bind(this);
    leitor.readAsDataURL(arquivo);    
  }

  _handleReaderLoaded(e) {
    let reader = e.target;
    let anexo = reader.result;
    let res = anexo.toString().split(",");
    let anexoDecode = res[1];
    this.arqImportacao.anexo = anexoDecode;
    this.importacao.arquivoimportacao = this.arqImportacao;
    console.log(this.importacao);
    this.pontoService.uploadArquivo(this.importacao).subscribe(res => { this.importacao });
  } 
}
