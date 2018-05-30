import { Usuario } from './../../shared/model/usuario';
import { ImportacaoService } from './../../services/importacao.service';
import { AnoMes } from './../../shared/model/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../shared/model/ponto';
import { ArquivoImportacao } from "../../shared/model/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../shared/model/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { DateFormatPipe } from '../../components/pipes/pipe';
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  usuario: Usuario = new Usuario();

  importacao: Importacao = new Importacao();

  importacoes: Importacao[] = new Array;

  arqImportacao: ArquivoImportacao = new ArquivoImportacao();

  constructor(private importacaoService: ImportacaoService) {
    this.usuario.nome = sessionStorage.getItem('nomeUsuario');

    this.listarImportacao();
  }

  ngOnInit() {

  }

  onUpload(event) {
    const arquivo = event.files[0];
    this.importacao.nome = arquivo.name;
    this.importacao.extensao = arquivo.name.substr(arquivo.name.lastIndexOf('.') + 1);
    const leitor = new FileReader();
    leitor.onload = this._handleReaderLoaded.bind(this);
    leitor.readAsDataURL(arquivo);
  }

  listarImportacao() {
    this.importacaoService.listarImportacao()
                  .subscribe(res => {
                             this.importacoes = res; });
  }

  _handleReaderLoaded(e) {
    const reader = e.target;
    const anexo = reader.result;
    const res = anexo.toString().split(",");
    const anexoDecode = res[1];
    this.arqImportacao.anexo = anexoDecode;
    this.importacao.arquivoimportacao = this.arqImportacao;
    this.importacaoService.uploadArquivo( this.importacao )
                  .subscribe(result => {
                            this.importacao = res; });
  }

}
