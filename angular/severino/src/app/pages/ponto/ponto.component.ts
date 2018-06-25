import { AnoMes } from './../../shared/models/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../shared/models/ponto';
import { ArquivoImportacao } from "../../shared/models/arquivoimportacao";
import { Importacao } from "../../shared/models/importacao";
import { Usuario } from '../../shared/models/usuario';
import { Legenda } from '../../shared/models/legenda';
import { MessageService } from 'primeng/components/common/messageservice';
import { TratamentoErrosService } from '../../services/tratamento-erros.service';
import { FormatarDataPipe } from '../../components/pipes/pipe';
import { ControleHorasService } from '../../services/controle-horas.service';
import { JornadaService } from '../../services/jornada.service';
import { Jornada } from '../../shared/models/jornada';
import { DiaSemana } from '../../shared/models/diasemana';
import { Message } from 'primeng/components/common/message';
import { LegendaService } from '../../services/legenda.service';
import { FormatarMinutosPipe } from '../../shared/components/pipes/time.pipe';
import { Justificativa } from '../../shared/models/justificativa';
import * as moment from 'moment/moment';
import { PontoEditado } from '../../shared/models/pontoEditado';
import { WorklogJira } from '../../shared/models/worklogJira';
import { Router } from '@angular/router';
import { PontoInf } from '../../shared/models/pontoInfo';
import { IssueInf } from '../../shared/models/issueInf';
import { Issues } from '../../shared/models/issues';
import { WorklogJiraService } from '../../services/worklogJira.service';
import { Issue } from '../../shared/models/issue';

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
  w: any = 0;
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
  dataFormatada: any;
  selectedIssues: Issues[] = [];
  worklogJira: WorklogJira = new WorklogJira;
  issues: Issues[];
  pontoEdicaoId: any;
  declaracao: any;
  pontosInf: PontoInf[] = [];
  pontoInf: PontoInf = new PontoInf();
  justificativaInf: Justificativa = new Justificativa();
  justDebito: boolean;
  justCredito: boolean;
  justJustificado: boolean;
  validaJust: Justificativa = new Justificativa();
  issuesOpcoes: IssueInf[] = [];
  issueOpcao: IssueInf = new IssueInf();
  lista: String[] = [];
  statusJust: any;
  worklogs: WorklogJira[] = [];

  constructor(
    private pontoService: PontoService,
    private messageService: MessageService,
    private tratamentoErrosService: TratamentoErrosService,
    private controleHorasService: ControleHorasService,
    private jornadaService: JornadaService,
    private legendaService: LegendaService,
    private formatarMinutosPipe: FormatarMinutosPipe,
    private router: Router,
    private formatarDataPipe: FormatarDataPipe,
    private worklogJiraService: WorklogJiraService,
  ) {

    this.cols = [
      { field: 'diaSemana', header: 'Dia', width: '7%' },
      { field: 'data', header: 'Data', width: '8%' },
      { field: 'jornadaId', header: 'Jor.', width: '6%' },
      { field: 'entrada1', header: 'Entrada', width: '6%' },
      { field: 'saida1', header: 'Saída', width: '6%' },
      { field: 'entrada2', header: 'Entrada', width: '6%' },
      { field: 'saida2', header: 'Saída', width: '6%' },
      { field: 'entrada3', header: 'Entrada', width: '6%' },
      { field: 'saida3', header: 'Saída', width: '6%' },
      { field: 'entrada4', header: 'Entrada', width: '6%' },
      { field: 'saida4', header: 'Saída', width: '6%' },
      { field: 'observacao', header: 'Observação', width: '20%' },
      { field: 'justificativaId', header: 'Just.' },
      { field: 'legenda', header: 'Leg.', width: '6%' },
    ];
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.departamento = sessionStorage.getItem('departamentoUsuario');
    this.funcao = sessionStorage.getItem('funcaoUsuario');
    this.usuario.pis = sessionStorage.getItem('pisUsuario');
    this.usuario.data_admissao = sessionStorage.getItem('dataAdmissao');

  }

  ngOnInit() {
    this.realizarConsultas();
  }

  showDialogLegenda() {
    this.displayLegenda = true;
  }

  showDialogJornada() {
    this.displayJornada = true;
  }

  showDialogJustificativa(ponto: any) {
    this.idPonto = ponto.id;
    this.statusJust = ponto.status;
    this.pontos.forEach(res => {
      if (res.id === this.idPonto) {
        this.data = res.data;
        this.dataFormatada = this.formatarDataPipe.transform(this.data);
        this.issues = res.issues;
      }
    });
    this.issues.forEach( res => {
      if (res.id) {
        this.selectedIssues.push(res);
      }
    });
    this.pontoEdicao = ponto;
    this.pontoEdicaoId = this.pontoEdicao.justificativa;
    if (this.pontoEdicaoId.id) {
      this.justificativa = this.pontoEdicao.justificativa;
    } else {
      this.justificativa = new Justificativa;
    }
    this.worklogs = [];
    this.worklogJiraService.listarIssues(this.usuario.id, this.data)
    .subscribe( worklog => {
      worklog.forEach( item => {
        item.startdate = this.formatarMinutosPipe.buscar(item.startdate, 11, 16) + " Tempo trabalhado: "
        + this.formatarMinutosPipe.horaTransform(item.timeworked);
        this.worklogs.push(item);
      });
    });
    this.displayJustificativa = true;
  }

  closeDialogJustificativa() {
    this.displayJustificativa = false;
    this.issues = [];
    this.selectedIssues = [];
  }

  gerarDeclaracaoExt(id: any, ano: string, mes: string, tipo: string = 'ext') {
    this.router.navigate(['usuario/ponto/declaracao'], { queryParams: { id, ano, mes, tipo } });
  }
  gerarDeclaracaoComp(id: any, ano: string, mes: string, tipo: string = 'comp') {
    this.router.navigate(['usuario/ponto/declaracao'], { queryParams: { id, ano, mes, tipo } });
  }
  gerarDeclaracaoJust(id: any, ano: string, mes: string, tipo: string = 'just') {
    this.router.navigate(['usuario/ponto/declaracao'], { queryParams: { id, ano, mes, tipo } });
  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  justificarPonto(justificativa: Justificativa) {
    this.pontos.forEach(ponto => {
      if (ponto.id === this.idPonto) {
        ponto.justificativa = justificativa;
        ponto.issues = [];
        ponto.issues = this.selectedIssues;
        this.pontoAux = ponto;
      }
    });
    this.pontoService.alterarPonto(this.pontoAux)
      .subscribe(res => {
        this.consultaPontoPorPeriodo();
        this.closeDialogJustificativa();
      }, error => {
        this.consultaPontoPorPeriodo();
        this.closeDialogJustificativa();
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      });
  }

  consultaPontoPorPeriodo() {
    this.pontoService.listarPontoPorPeriodo(this.usuario.id, this.ano, this.mes)
      .subscribe(res => {
        this.pontos = [];
        this.pontos = res;
        this.verificarStatus(res);
        this.pontosEditados = [];
        this.montaObjeto(res);
        this.pontosInf = [];
        this.montaPontoInf(res);
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
        this.mensagemGrow = "";
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      }, error => {
        this.pontosEditados = [];
        this.absenteismo = '';
        this.horasAbono = '';
        this.horasTrabalhadas = '';
        this.horasSaldoMes = '';
        this.horasDebito = '';
        this.horasCredito = '';
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      });
  }

  montaPontoInf(pontos: any[]) {
    pontos.forEach(ponto => {
      this.pontoInf = new PontoInf();
      this.pontoInf.status = ponto.status;
      this.pontoInf.data = ponto.data;
      this.pontoInf.minutosTrab = ponto.minutos_trabalhados;
      if (ponto.justificativa) {
        this.justificativaInf = ponto.justificativa;
        if (this.justificativaInf.descricao) {
          this.pontoInf.justificativa = this.justificativaInf;
          this.pontoInf.temJust = true;
        }
      }
      this.pontosInf.push(this.pontoInf);
    });
    this.validaBtDeclaracoes(this.pontosInf);
  }

  validaBtDeclaracoes(pontos: any[]) {
    this.justDebito = false;
    this.justCredito = false;
    this.justJustificado = false;
    pontos.forEach(res => {
      if (res.justificativa) {
        this.validaJust = new Justificativa();
        this.validaJust = res.justificativa;
        if (this.validaJust.descricao && res.status === 'DEBITO') {
          this.justDebito = true;
        } else if (this.validaJust.descricao && res.status === 'CREDITO') {
          this.justCredito = true;
        } else if (this.validaJust.descricao && res.status === 'MARCACAO_INCORRETA') {
          this.justJustificado = true;
        }
      }
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
    let worklogs: WorklogJira = new WorklogJira();
    pontos.forEach(ponto => {
      this.pontoEditado = new PontoEditado();
      jornada = ponto.jornada;
      if (ponto.justificativa) {
        this.pontoEditado.justificativa = ponto.justificativa;
        justificativa = ponto.justificativa;
      } else {
        this.pontoEditado.justificativa = new Justificativa();
      }
      worklogs = ponto.worklogs;
      legenda = ponto.legenda;
      diaSemana = ponto.diasemana;
      this.pontoEditado.issues = ponto.issues;
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
    this.pontosEditados.forEach(data => {
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
        });
      }, error => {
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
        });
      }, error => {
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

      if (ponto.entrada1.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      } else if (ponto.entrada2.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      } else if (ponto.entrada3.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      } else if (ponto.entrada4.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      } else if (ponto.saida1.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      } else if (ponto.saida2.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      } else if (ponto.saida3.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      } else if (ponto.saida4.indexOf("*") !== -1) {
        this.existeMarcInc = true;
      }
    });
  }
}
