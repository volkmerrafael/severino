import { AnoMes } from './../../model/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../model/ponto';
import { ArquivoImportacao } from "../../model/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../model/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { Usuario } from '../../model/usuario';
import { DateFormatPipe } from '../../components/pipes/pipe';
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { Legenda } from '../../model/legenda';
import { MessageService } from 'primeng/components/common/messageservice';
import { TratamentoErrosService } from '../../services/tratamento-erros.service';

@Component({
  selector: 'app-ponto',
  templateUrl: './ponto.component.html',
  styleUrls: ['./ponto.component.css']
})
export class PontoComponent implements OnInit {
  myDate: Date = new Date();
  mes: String = "" + this.myDate.getMonth();
  ano: String = "" + this.myDate.getFullYear();
  pontos: Ponto[] = [];
  periodos: AnoMes[] = [];
  periodoSelecionado: AnoMes;
  usuario: Usuario = new Usuario();
  importacao: Importacao = new Importacao();
  arqImportacao: ArquivoImportacao = new ArquivoImportacao();
  legendas: Legenda[] = [];
  cols: any[] = [];
  cu1: string;
  cu2: string;
  mensagemGrow: any;
  tituloGrow: any;
  tipoGrow: any;

  constructor(
    private pontoService: PontoService,
    private messageService: MessageService,
    private tratamentoErrosService: TratamentoErrosService,
  ) {

    this.cols = [
      { field: 'data', header: 'Data', width: '8%' },
      { field: 'diasemana', header: 'Dia da Semana', width: '6%' },
      { field: 'jornada', header: 'Jornada', width: '12%' },
      { field: 'entrada1', header: 'Entrada', width: '6%' },
      { field: 'saida1', header: 'Saída', width: '6%' },
      { field: 'entrada2', header: 'Entrada', width: '6%' },
      { field: 'saida2', header: 'Saída', width: '6%' },
      { field: 'entrada3', header: 'Entrada', width: '6%' },
      { field: 'saida3', header: 'Saída', width: '6%' },
      { field: 'entrada4', header: 'Entrada', width: '6%' },
      { field: 'saida4', header: 'Saída', width: '6%' },
      { field: 'status', header: 'Status', width: '11%' },
      { field: 'observacao', header: 'Observação', width: '10%' }
    ];

    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.cu1 = sessionStorage.getItem('departamentoUsuario');
    this.cu2 = sessionStorage.getItem('funcaoUsuario');
    this.usuario.pis = sessionStorage.getItem('pisUsuario');
    this.usuario.data_admissao = sessionStorage.getItem('dataAdmissao');

    this.pontoService.listarPontoPorPeriodo(this.ano, this.mes)
      .subscribe(res => {
        this.pontos = res;
      }, error => {
        this.tratamentoErrosService.handleError(error);
      });
  }

  ngOnInit() { }

  showSuccess(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  listarPeriodos() {
    this.pontoService.listarPeriodos()
      .subscribe(res => {
        console.log(res)
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
        this.mensagemGrow = "";
        this.showSuccess(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
        this.periodos = res;
      }, error => {
        this.tratamentoErrosService.handleError(error);
      });
  }

  listarPontoPorPeriodo() {
    this.pontoService.listarPontoPorPeriodo(this.ano, this.mes)
      .subscribe(res => {
        console.log(res)
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
        this.mensagemGrow = "";
        this.showSuccess(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
        this.pontos = res;
      }, error => {
        this.tratamentoErrosService.handleError(error);
      });
  }
}
