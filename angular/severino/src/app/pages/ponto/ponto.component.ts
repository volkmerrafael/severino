import { AnoMes } from './../../shared/models/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../shared/models/ponto';
import { ArquivoImportacao } from "../../shared/models/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../shared/models/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem, SelectItem } from 'primeng/api';
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
import { Jornada } from '../../shared/models/jornada';
import { DiaSemana } from '../../shared/models/diasemana';
import { Message } from 'primeng/components/common/message';
import { LegendaService } from '../../services/legenda.service';
import { FormatarMinutosPipe } from '../../shared/components/pipes/time.pipe';

@Component({
  selector: 'app-ponto',
  templateUrl: './ponto.component.html',
  styleUrls: ['./ponto.component.css'],
  providers: [FormatarDataPipe, FormatarMinutosPipe]
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
  horasSaldoMes: string;
  saldoNegativo: boolean;
  horasTrabalhadas: string;
  horasAbono: string;
  jornadas: Jornada[] = [];
  n: any = 0;
  l: any = 0;
  existeCorreto: any = false;
  existeDebito: any = false;
  existeCredito: any = false;
  existeJustificado: any = false;
  existeFaltaJust: any = false;
  existeMarcInc: any = false;
  existeSemInf: any = false;
  existeFerias: any = false;
  existeAtestado: any = false;
  existeFacultativo: any = false;
  existeNaoAdimitido: any = false;
  existeFeriado: any = false;
  displayLegenda: any = false;
  displayJornada: any = false;
  msgs: Message[];

  constructor(
    private pontoService: PontoService,
    private messageService: MessageService,
    private tratamentoErrosService: TratamentoErrosService,
    private formatarDataPipe: FormatarDataPipe,
    private controleHorasService: ControleHorasService,
    private jornadaService: JornadaService,
    private legendaService: LegendaService,
    private formatarMinutosPipe: FormatarMinutosPipe,
  ) {

    this.cols = [
      { field: 'data', pipe: 'formatarData', header: 'Data', width: '6%'},
      { field: 'entrada1', header: 'Entrada', width: '10%'},
      { field: 'saida1', header: 'Saída', width: '6%'},
      { field: 'entrada2', header: 'Entrada', width: '6%'},
      { field: 'saida2', header: 'Saída', width: '6%'},
      { field: 'entrada3', header: 'Entrada', width: '6%'},
      { field: 'saida3', header: 'Saída', width: '6%'},
      { field: 'entrada4', header: 'Entrada', width: '6%'},
      { field: 'saida4', header: 'Saída', width: '6%'},
      { field: 'observacao', header: 'Observação', width: '6%'}
    ];

    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.departamento = sessionStorage.getItem('departamentoUsuario');
    this.funcao = sessionStorage.getItem('funcaoUsuario');
    this.usuario.pis = sessionStorage.getItem('pisUsuario');
    this.usuario.data_admissao = sessionStorage.getItem('dataAdmissao');

  }

  showDialogLegenda() {
    this.displayLegenda = true;
  }

  showDialogJornada() {
    this.displayJornada = true;
  }

  ngOnInit() {
    this.realizarConsultas();
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
        this.verificarStatus(res);
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
      if (res != null) {
        this.horasCredito = this.formatarMinutosPipe.transform(res.credito);
        this.horasDebito = this.formatarMinutosPipe.transform(res.debito);
        this.horasSaldoMes = this.formatarMinutosPipe.transform(res.saldo);
        this.saldoNegativo = res.negativo;
        this.horasTrabalhadas = this.formatarMinutosPipe.transform(res.trabalhadas);
        this.horasAbono = this.formatarMinutosPipe.transform(res.abono);
      }
    }, error => {
        this.tratamentoErrosService.handleError(error);
      });
  }

  consultaJornada() {
    this.jornadas = [];
    this.jornadaService.consultaJornada(this.ano, this.mes)
    .subscribe(res => {
      this.n = 0;
      res.forEach(jornada => {
        this.jornadas[this.n] = jornada;
        this.n = this.n + 1;
    }); }, error => {
      this.tratamentoErrosService.handleError(error);
  });
}

  consultaLegenda() {
    this.legendas = [];
    this.legendaService.consultaLegenda(this.ano, this.mes)
    .subscribe(res => {
      this.l = 0;
      res.forEach(legenda => {
        this.legendas[this.l] = legenda;
        this.l = this.l + 1;
    }); }, error => {
      this.tratamentoErrosService.handleError(error);
  });
  }

  realizarConsultas() {
    this.consultaControleHoras();
    this.consultaJornada();
    this.consultaPontoPorPeriodo();
    this.consultaLegenda();
  }

  verificarStatus(pontos: Ponto[]) {
    this.existeCorreto = false;
    this.existeDebito = false;
    this.existeCredito = false;
    this.existeJustificado = false;
    this.existeFaltaJust = false;
    this.existeMarcInc = false;
    this.existeSemInf = false;
    this.existeFerias = false;
    this.existeAtestado = false;
    this.existeFacultativo = false;
    this.existeNaoAdimitido = false;
    this.existeFeriado = false;
      pontos.forEach(ponto => {
      if (ponto.status === 'CORRETO') {
        this.existeCorreto = true;
      }
      if (ponto.status === 'DEBITO') {
        this.existeDebito = true;
      }
      if (ponto.status === 'CREDITO') {
        this.existeCredito = true;
      }
      if (ponto.status === 'JUSTIFICADO') {
        this.existeJustificado = true;
      }
      if (ponto.status === 'FALTA_JUSTIFICADA') {
        this.existeFaltaJust = true;
      }
      if (ponto.status === 'MARCACAO_INCORRETA') {
        this.existeMarcInc = true;
      }
      if (ponto.status === 'SEM_INFORMACAO') {
        this.existeSemInf = true;
      }
      if (ponto.status === 'ATESTADO_MEDICO') {
        this.existeAtestado = true;
      }
      if (ponto.status === 'PONTO_FACULTATIVO') {
        this.existeFacultativo = true;
      }
      if (ponto.status === 'FERIAS') {
        this.existeFerias = true;
      }
      if (ponto.status === 'NAO_ADMITIDO') {
        this.existeNaoAdimitido = true;
      }
      if (ponto.status === 'FERIADO') {
        this.existeFeriado = true;
      }
    });
  }
}
