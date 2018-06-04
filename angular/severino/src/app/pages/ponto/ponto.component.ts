import { AnoMes } from './../../shared/models/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../shared/models/ponto';
import { ArquivoImportacao } from "../../shared/models/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../shared/models/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { Usuario } from '../../shared/models/usuario';
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { Legenda } from '../../shared/models/legenda';
import { MessageService } from 'primeng/components/common/messageservice';
import { TratamentoErrosService } from '../../services/tratamento-erros.service';
import { FormatarDataPipe } from '../../components/pipes/pipe';
import { ControleHorasService } from '../../services/controle-horas.service';
import { ControleHoras } from '../../shared/models/controle-horas';
import { JornadaService } from '../../services/jornada.service';
@Component({
  selector: 'app-ponto',
  templateUrl: './ponto.component.html',
  styleUrls: ['./ponto.component.css'],
  providers: [FormatarDataPipe]
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
  departamento: string;
  funcao: string;
  mensagemGrow: any;
  tituloGrow: any;
  tipoGrow: any;
  horasCredito: string;
  horasDebito: string;
  jornada: string;

  constructor(
    private pontoService: PontoService,
    private messageService: MessageService,
    private tratamentoErrosService: TratamentoErrosService,
    private formatarDataPipe: FormatarDataPipe,
    private controleHorasService: ControleHorasService,
    private jornadaService: JornadaService,
  ) {

    this.cols = [
      { field: 'data', pipe: 'formatarData', header: 'Data'},
      { field: 'diasemana', header: 'Dia da Semana'},
      { field: 'entrada1', header: 'Entrada'},
      { field: 'saida1', header: 'Saída'},
      { field: 'entrada2', header: 'Entrada'},
      { field: 'saida2', header: 'Saída'},
      { field: 'entrada3', header: 'Entrada'},
      { field: 'saida3', header: 'Saída'},
      { field: 'entrada4', header: 'Entrada'},
      { field: 'saida4', header: 'Saída' },
      { field: 'observacao', header: 'Observação'}
    ];

    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.departamento = sessionStorage.getItem('departamentoUsuario');
    this.funcao = sessionStorage.getItem('funcaoUsuario');
    this.usuario.pis = sessionStorage.getItem('pisUsuario');
    this.usuario.data_admissao = sessionStorage.getItem('dataAdmissao');

  }

  ngOnInit() {
    this.consultaPontoPorPeriodo();
   }

  showSuccess(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  consultaPontoPorPeriodo() {
    this.pontoService.listarPontoPorPeriodo(this.ano, this.mes)
      .subscribe(res => {
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
        this.mensagemGrow = "";
        this.showSuccess(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
        this.pontos = res;
        this.pontos.forEach(ponto => {
          ponto.data = this.formatarDataPipe.transform(ponto.data);
    });
      }, error => {
        this.tratamentoErrosService.handleError(error);
      });
  }

  consultaControleHoras() {
    this.controleHorasService.controleHoras(this.ano, this.mes)
    .subscribe(res => {
      this.horasCredito = res.horas_credito;
      this.horasDebito = res.horas_debito;
    }, error => {
        this.tratamentoErrosService.handleError(error);
      });
  }

  consultaJornada() {
    this.jornadaService.consultaJornada(this.ano, this.mes)
    .subscribe(res => {
      res.forEach(jornada => {
        this.jornada = jornada.periodo_jornada;
      });
    }, error => {
      this.tratamentoErrosService.handleError(error);
    });
  }

  listarConsultas() {
    this.consultaControleHoras();
    this.consultaJornada();
    this.consultaPontoPorPeriodo();
  }
}
