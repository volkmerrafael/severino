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
import { Router, ActivatedRoute } from '@angular/router';
import { PontoInf } from '../../shared/models/pontoInfo';
import { IssueInf } from '../../shared/models/issueInf';
import { Issues } from '../../shared/models/issues';
import { WorklogJiraService } from '../../services/worklogJira.service';
import { UsuarioService } from '../../services/usuario.service';
import { Tabela } from '../../shared/models/tabela';

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
  usuario: Usuario = new Usuario();
  importacao: Importacao = new Importacao();
  legendas: Legenda[] = [];
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
  lista: String[] = [];
  statusJust: any;
  worklogs: WorklogJira[] = [];
  idRota: any;
  rotaValue: any;
  rota: any;
  tipo: any;
  nomeUsuario: string;
  tabela: Tabela[] = [];
  entrada1: any = false;
  entrada2: any = false;
  entrada3: any = false;
  entrada4: any = false;
  saida1: any = false;
  saida2: any = false;
  saida3: any = false;
  saida4: any = false;

  constructor(
    private pontoService: PontoService,
    private messageService: MessageService,
    private controleHorasService: ControleHorasService,
    private jornadaService: JornadaService,
    private legendaService: LegendaService,
    private formatarMinutosPipe: FormatarMinutosPipe,
    private router: Router,
    private formatarDataPipe: FormatarDataPipe,
    private worklogJiraService: WorklogJiraService,
    private route: ActivatedRoute,
    private usuarioServive: UsuarioService,
  ) {

    this.tipo = sessionStorage.getItem('tipo');
    this.usuario.id = parseInt(sessionStorage.getItem('id'), 10);
    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.departamento = sessionStorage.getItem('departamentoUsuario');
    this.funcao = sessionStorage.getItem('funcaoUsuario');
    this.usuario.pis = sessionStorage.getItem('pisUsuario');
    this.usuario.data_admissao = sessionStorage.getItem('dataAdmissao');

  }

  ngOnInit() {
    this.rota = this.route.queryParams;
    this.rotaValue = this.rota.value;
    this.idRota = this.rotaValue.id;
    if ( this.idRota ) {
      this.usuario.id = this.idRota;
      this.usuarioServive.usuario(this.usuario.id)
      .subscribe( res => {
        this.nomeUsuario = res.nome;
      });
    }
    this.realizarConsultas();
  }

  montarTabela() {
    this.tabela = [];
    this.tabela.push({ field: 'diaSemana', header: 'Dia', width: '7%' });
    this.tabela.push({ field: 'data', header: 'Data', width: '8%' });
    this.tabela.push({ field: 'jornadaId', header: 'Jor.', width: '6%' });
    if (this.entrada1) {
    this.tabela.push({ field: 'entrada1', header: 'Entrada', width: '6%' });
    } if (this.saida1) {
    this.tabela.push({ field: 'saida1', header: 'Saída', width: '6%' });
    } if (this.entrada2) {
    this.tabela.push({ field: 'entrada2', header: 'Entrada', width: '6%' });
    } if (this.saida2) {
    this.tabela.push({ field: 'saida2', header: 'Saída', width: '6%' });
    } if (this.entrada3) {
    this.tabela.push({ field: 'entrada3', header: 'Entrada', width: '6%' });
    } if (this.saida3) {
    this.tabela.push({ field: 'saida3', header: 'Saída', width: '6%' });
    } if (this.entrada4) {
    this.tabela.push({ field: 'entrada4', header: 'Entrada', width: '6%' });
    } if (this.saida4) {
    this.tabela.push({ field: 'saida4', header: 'Saída', width: '6%' });
    }
    this.tabela.push({ field: 'worklog_diario', header: 'Worklog', width: '8%' });
    this.tabela.push({ field: 'observacao', header: 'Observação', width: ' ' });
    this.tabela.push({ field: 'justificativaId', header: 'Just.', width: '6%' });
    this.tabela.push({ field: 'legenda', header: 'Leg.', width: '6%' });
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

  gerarDeclaracao(id: any, ano: string, mes: string, tipo: string ) {
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
        console.log(res);
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
        this.consultaColunas(res);
        this.montarTabela();
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

  consultaColunas(pontos: any[]) {
    this.entrada1 = false;
    this.entrada2 = false;
    this.entrada3 = false;
    this.entrada4 = false;
    this.saida1 = false;
    this.saida2 = false;
    this.saida3 = false;
    this.saida4 = false;
      pontos.forEach(ponto => {
        if (ponto.entrada1) {
          this.entrada1 = true;
        }
        if (ponto.entrada2) {
          this.entrada2 = true;
        }
        if (ponto.entrada3) {
          this.entrada3 = true;
        }
        if (ponto.entrada4) {
          this.entrada4 = true;
        }
        if (ponto.saida1) {
          this.saida1 = true;
        }
        if (ponto.saida2) {
          this.saida2 = true;
        }
        if (ponto.saida3) {
          this.saida3 = true;
        }
        if (ponto.saida4) {
          this.saida4 = true;
        }
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
      this.pontoEditado.data = moment(ponto.data).format("DD/MM/YYYY");
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
      this.pontoEditado.worklog_diario = ponto.worklog_diario;
      if (legenda !== undefined) {
        this.pontoEditado.legenda = legenda.sigla;
      }
      this.pontoEditado.status = ponto.status;
      this.pontosEditados.push(this.pontoEditado);
    });
  }

  consultaJornada() {
    this.jornadas = [];
    this.jornadaService.consultaJornada(this.usuario.id, this.ano, this.mes)
      .subscribe(res => {
        res.forEach(jornada => {
          this.jornadas.push(jornada);
        });
      }, error => {
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
        res.forEach(legenda => {
          this.legendas.push(legenda);
        });
      }, error => {
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
      switch (ponto.status) {
        case 'CORRETO':  this.existeCorreto = true; break;
        case 'DEBITO':  this.existeDebito = true; break;
        case 'CREDITO': this.existeCredito = true; break;
        case 'JUSTIFICADO': this.existeJustificado = true; break;
        case 'FALTA_JUSTIFICADA': this.existeFaltaJust = true; break;
        case 'MARCACAO_INCORRETA': this.existeMarcInc = true; break;
        case 'SEM_INFORMACAO': this.existeSemInf = true; break;
        case 'ATESTADO_MEDICO': this.existeAtestado = true; break;
        case 'PONTO_FACULTATIVO': this.existeFacultativo = true; break;
        case 'FERIAS': this.existeFerias = true; break;
        case 'NAO_ADMITIDO': this.existeNaoAdimitido = true; break;
        case 'FERIADO': this.existeFeriado = true; break;
        case 'CERTIDAO_DE_OBITO': this.existeObito = true; break;
        case 'COMPENSACAO': this.existeCompensacao = true; break;
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
