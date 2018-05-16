import { Usuario } from './../../model/usuario';
import { ImportacaoService } from './../../services/importacao.service';
import { AnoMes } from './../../model/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../model/ponto';
import { ArquivoImportacao } from "../../model/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../model/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { DateFormatPipe } from '../../components/pipes/pipe'
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  usuario: Usuario = new Usuario();

  importacao: Importacao = new Importacao(); 

  importacoes: Importacao[] = [];

  arqImportacao: ArquivoImportacao = new ArquivoImportacao();  

  constructor(private ImportacaoService: ImportacaoService) { 
    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    
    this.ImportacaoService.listarImportacao()
                  .subscribe(res => { 
                             this.importacoes = res });
  }

  ngOnInit() {

  }

  onBasicUpload(event) {
    let arquivo = event.files[0];
    this.importacao.nome = arquivo.name;
    this.importacao.extensao = arquivo.name.substr(arquivo.name.lastIndexOf('.')+1);    
    let leitor = new FileReader();
    leitor.onload = this._handleReaderLoaded.bind(this);
    leitor.readAsDataURL(arquivo);    
  }

  listarImportacao(event) {
    this.ImportacaoService.listarImportacao().subscribe(res => { this.importacao });
    
  }

  _handleReaderLoaded(e) {
    let reader = e.target;
    let anexo = reader.result;
    let res = anexo.toString().split(",");
    let anexoDecode = res[1];
    this.arqImportacao.anexo = anexoDecode;
    this.importacao.arquivoimportacao = this.arqImportacao;
    console.log(this.importacao);
    this.ImportacaoService.uploadArquivo(this.importacao).subscribe(res => { this.importacao });
    this.ImportacaoService.listarImportacao()
                  .subscribe(res => { 
                             this.importacoes = res });
  } 

}
