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
import { JustificativaService } from '../../services/justificativa.service';
import { Justificativa } from '../../shared/models/justificativa';
import * as moment from 'moment/moment';
import { PontoEditado } from '../../shared/models/pontoEditado';
import { WorklogJiraService } from '../../services/worklogJira.service';
import { WorklogJira } from '../../shared/models/worklogJira';

@Component({
  selector: 'app-ponto',
  templateUrl: './ponto.component.html',
  styleUrls: ['./ponto.component.css'],
  providers: [FormatarDataPipe, FormatarMinutosPipe]
})
export class PontoComponent implements OnInit {
  myDate: Date = new Date();
  mes: String = "" + (this.myDate.getMonth() + 1);
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
  absenteismo: string;
  jornadas: Jornada[] = [];
  n: any = 0;
  l: any = 0;
  w: any;
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
  existeObito: any = false;
  existeCompensacao: any = false;
  displayLegenda: any = false;
  displayJornada: any = false;
  displayJustificativa: any = false;
  msgs: Message[];
  justificativa: Justificativa = new Justificativa;
  pontoEditado: PontoEditado = new PontoEditado();
  pontosEditados: PontoEditado[] = [];
  pontoAux: Ponto = new Ponto();
  pontoEdicao: PontoEditado = new PontoEditado();
  idPonto: any;
  data: any;
  dia: any;
  listaIssues: WorklogJira[];
  listaIssueJson: any;
  selectedIssues: WorklogJira[];

  constructor(
    private pontoService: PontoService,
    private messageService: MessageService,
    private tratamentoErrosService: TratamentoErrosService,
    private formatarDataPipe: FormatarDataPipe,
    private controleHorasService: ControleHorasService,
    private jornadaService: JornadaService,
    private legendaService: LegendaService,
    private formatarMinutosPipe: FormatarMinutosPipe,
    private justificativaService: JustificativaService,
    private worklogJiraService: WorklogJiraService
  ) {

    this.cols = [
      { field: 'diaSemana', header: 'Dia', width: '7%'},
      { field: 'data', header: 'Data', width: '8%'},
      { field: 'jornadaId', header: 'Jor.', width: '6%'},
      { field: 'entrada1', header: 'Entrada', width: '6%'},
      { field: 'saida1', header: 'Saída', width: '6%'},
      { field: 'entrada2', header: 'Entrada', width: '6%'},
      { field: 'saida2', header: 'Saída', width: '6%'},
      { field: 'entrada3', header: 'Entrada', width: '6%'},
      { field: 'saida3', header: 'Saída', width: '6%'},
      { field: 'entrada4', header: 'Entrada', width: '6%'},
      { field: 'saida4', header: 'Saída', width: '6%'},
      { field: 'observacao', header: 'Observação', width: '20%'},
      { field: 'justificativaId', header: 'Just.'},
      { field: 'legenda', header: 'Leg.', width: '6%'},
    ];
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
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

  showDialogJustificativa(ponto: any) {
    this.idPonto = ponto.id;
    this.pontos.forEach(res => {
      if (res.id === this.idPonto) {
        this.data = res.data;
      }
    });
    this.worklogJiraService.listarIssues(this.usuario.id, this.data)
    .subscribe( res => {
      this.listaIssues = res;
      this.listaIssues.forEach( issue => {
        issue.issue = issue.issue + " - " + issue.summary + " (Iniciado em: " + issue.startdate +
        " Tempo trabalhado: " + issue.timeworked + ")";
      });
    });
    this.pontoEdicao = ponto;
    if (this.pontoEdicao.justificativa) {
      this.justificativa = this.pontoEdicao.justificativa;
    } else {
      this.justificativa = new Justificativa;
    }
    this.displayJustificativa = true;
  }

  closeDialogJustificativa() {
    this.displayJustificativa = false;
  }

  ngOnInit() {
    this.realizarConsultas();
   }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  justificarPonto(justificativa: Justificativa) {
    this.pontos.forEach( ponto => {
      if (ponto.id === this.idPonto) {
        ponto.justificativa = justificativa;
        this.pontoAux = ponto;
      }
    });
    this.pontoService.alterarPonto(this.pontoAux)
    .subscribe( res => {
      this.consultaPontoPorPeriodo();
      this.closeDialogJustificativa();
    }, error => {
      this.tratamentoErrosService.handleError(error);
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
  }

  consultaPontoPorPeriodo() {
    this.pontoService.listarPontoPorPeriodo(this.usuario.id, this.ano, this.mes)
      .subscribe(res => {
        this.pontos = res;
        this.verificarStatus(res);
        this.pontosEditados = [];
        this.montaObjeto(res);
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
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

  consultaControleHoras() {
    this.controleHorasService.controleHoras(this.usuario.id, this.ano, this.mes)
    .subscribe(res => {
      if (res != null) {
        this.horasCredito = this.formatarMinutosPipe.transform(res.credito);
        this.horasDebito = this.formatarMinutosPipe.transform(res.debito);
        this.horasSaldoMes = this.formatarMinutosPipe.transform(res.saldo);
        if (res.saldo < 0 && res.saldo !== undefined) {
          this.horasSaldoMes = this.horasSaldoMes + " Débito";
        } else if (res.saldo > 0 && res.saldo !== undefined) {
          this.horasSaldoMes = this.horasSaldoMes + " Crédito";
        }
        this.saldoNegativo = res.negativo;
        this.horasTrabalhadas = this.formatarMinutosPipe.transform(res.trabalhadas);
        this.horasAbono = this.formatarMinutosPipe.transform(res.abono);
        this.absenteismo = res.absenteismo + "%";
      }
    }, error => {
      this.tratamentoErrosService.handleError(error);
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
  }

  montaObjeto(pontos: any[]) {
    let jornada: Jornada = new Jornada();
    let legenda: Legenda = new Legenda();
    let diaSemana: DiaSemana = new DiaSemana;
    let justificativa: Justificativa = new Justificativa();
    pontos.forEach( ponto => {
      this.pontoEditado = new PontoEditado();
      jornada = ponto.jornada;
      if (ponto.justificativa) {
        this.pontoEditado.justificativa = ponto.justificativa;
        justificativa = ponto.justificativa;
      } else {
        this.pontoEditado.justificativa = new Justificativa();
      }
      legenda = ponto.legenda;
      diaSemana = ponto.diasemana;
      this.pontoEditado.id = ponto.id;
      this.pontoEditado.diaSemana = diaSemana.nome;
      this.pontoEditado.data = ponto.data;
      if (jornada !== undefined) {
      this.pontoEditado.jornadaId = jornada.id;
      }
      this.pontoEditado.entrada1 = ponto.entrada1;
      this.pontoEditado.saida1 = ponto.saida1;
      this.pontoEditado.entrada2 = ponto.entrada2;
      this.pontoEditado.saida2 = ponto.saida2;
      this.pontoEditado.entrada3 = ponto.entrada3;
      this.pontoEditado.saida3 = ponto.saida3;
      this.pontoEditado.entrada4 = ponto.entrada4;
      this.pontoEditado.saida4 = ponto.saida4;
      this.pontoEditado.observacao = ponto.observacao;
      if (legenda !== undefined) {
      this.pontoEditado.legenda = legenda.sigla;
      }
      this.pontoEditado.status = ponto.status;
      this.pontosEditados.push(this.pontoEditado);
    });
    this.pontosEditados.forEach( data => {
        data.data = moment(data.data).format("DD/MM/YYYY");
    });
  }

  consultaJornada() {
    this.jornadas = [];
    this.jornadaService.consultaJornada(this.usuario.id, this.ano, this.mes)
    .subscribe(res => {
      this.n = 0;
      res.forEach(jornada => {
        this.jornadas[this.n] = jornada;
        this.n = this.n + 1;
    }); }, error => {
      this.tratamentoErrosService.handleError(error);
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
}

  consultaLegenda() {
    this.legendas = [];
    this.legendaService.consultaLegenda(this.usuario.id, this.ano, this.mes)
    .subscribe(res => {
      this.l = 0;
      res.forEach(legenda => {
        this.legendas[this.l] = legenda;
        this.l = this.l + 1;
    }); }, error => {
      this.tratamentoErrosService.handleError(error);
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = error.error;
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
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
    this.existeObito = false;
    this.existeCompensacao = false;
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
      if (ponto.status === 'CERTIDAO_DE_OBITO') {
        this.existeObito = true;
      }
      if (ponto.status === 'COMPENSACAO') {
        this.existeCompensacao = true;
      }
    });
  }
}
