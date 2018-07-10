import { Usuario } from './../../shared/models/usuario';
import { ImportacaoService } from './../../services/importacao.service';
import { AnoMes } from './../../shared/models/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../shared/models/ponto';
import { ArquivoImportacao } from "../../shared/models/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../shared/models/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { FormatarDataPipe } from '../../components/pipes/pipe';
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TratamentoErrosService } from '../../services/tratamento-erros.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { Message } from 'primeng/components/common/message';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  usuario: Usuario = new Usuario();
  importacao: Importacao = new Importacao();
  importacoes: any;
  arqImportacao: ArquivoImportacao = new ArquivoImportacao();
  mensagemGrow: any;
  tituloGrow: any;
  tipoGrow: any;
  token: any;
  msgs: Message[];
  uploadedFiles: any[] = [];
  cols: any[];

  constructor(
    private importacaoService: ImportacaoService,
    private tratamentoErrosService: TratamentoErrosService,
    private messageService: MessageService,
  ) {
    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
  }

  ngOnInit() {
    this.listarImportacao();
  }
  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  listarImportacao() {
    this.importacaoService.listarImportacao()
      .subscribe(res => {
        this.importacoes = res;
        this.cols = [
          { field: 'importacoes.nome', header: 'Nome' },
          { field: 'importacoes.tamanho', header: 'Tamanho' },
          { field: 'importacoes.inicio_periodo', header: 'Início' },
          { field: 'importacoes.final_periodo', header: 'Final' },
          { field: 'importacoes.data_hora_importacao', header: 'Data/Hora' },
          { field: 'importacoes.quantidade_usuario', header: 'Usu.' },
          { field: 'importacoes.usuario_com_debito_banco', header: 'Déb.' },
          { field: 'importacoes.usuario_com_credito_banco', header: 'Cré.' },
          { field: 'importacoes.usuario_com_marcacao_incorreta', header: 'Inc.' },
          { field: 'importacoes.usuario_sem_pendencias', header: 'Cor.' },
          { field: 'importacoes.tempo_importacao', header: 'Tempo Imp.' },
          { field: 'importacoes.tempo_processamento', header: 'Tempo Proc. Arq.' },
          { field: 'importacoes.status', header: 'Sit.' },
        ];
        this.tipoGrow = "success";
        this.tituloGrow = 'Atualizado';
        this.mensagemGrow = "";
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      }, error => {
        this.tratamentoErrosService.handleError(error);
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      });
  }

  onUpload(event, form) {
    for (const file of event.files) {
      this.uploadedFiles.push(file);
    }
    const arquivo = event.files[0];
    this.importacao.nome = arquivo.name;
    this.importacao.extensao = arquivo.name.substr(arquivo.name.lastIndexOf('.') + 1);
    const leitor = new FileReader();
    leitor.onload = this._handleReaderLoaded.bind(this);
    leitor.readAsDataURL(arquivo);
    form.clear();
  }

  _handleReaderLoaded(e) {
    const reader = e.target;
    const anexo = reader.result;
    const res = anexo.toString().split(",");
    const anexoDecode = res[1];
    this.arqImportacao.anexo = anexoDecode;
    this.importacao.arquivoimportacao = this.arqImportacao;
    this.importacaoService.uploadArquivo(this.importacao)
    .subscribe(result => {
      this.tipoGrow = "success";
      this.tituloGrow = 'Sucesso';
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    }, error => {
      this.tratamentoErrosService.handleError(error);
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
  }
}
