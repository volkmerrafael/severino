import { Component, OnInit } from '@angular/core';
import { Message } from 'primeng/components/common/message';
import { PrioridadeJira } from '../../../../shared/models/prioridadeJira';
import { MessageService } from 'primeng/components/common/messageservice';
import { TratamentoErrosService } from '../../../../services/tratamento-erros.service';
import { JiraService } from '../../jira.service';
import * as moment from 'moment/moment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  mensagemGrow: any;
  tituloGrow: any;
  tipoGrow: any;
  token: any;
  msgs: Message[];
  uploadedFiles: any[] = [];
  cols: any[];
  prioridadesJira: any;

  constructor(
    private jiraService: JiraService,
    private tratamentoErrosService: TratamentoErrosService,
    private messageService: MessageService
  ) { }

  ngOnInit() {
    this.listarJiraPrioridade();
  }
  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  listarJiraPrioridade() {
    this.jiraService.listaPrioridadeJira()
      .subscribe(res => {
        console.log(res);
        this.prioridadesJira = res;
        this.prioridadesJira.forEach(data => {
        data.pendenteem = moment(data.pendenteem).format("DD/MM/YYYY");
        });
        console.log('Jira', this.prioridadesJira);
        this.cols = [
          { field: 'project_name', header: 'Projeto' },
          { field: 'sitema', header: 'Sistema' },
          { field: 'issueenum', header: 'Num. Issue' },
          { field: 'summary', header: 'Título da Issue' },
          { field: 'cliente', header: 'Cliente' },
          { field: 'priorityid', header: 'Prioridade' },
          { field: 'pendenteem', header: 'Data Pendente' },
          { field: 'ano_restante', header: 'Ano Restante' },
          { field: 'temp_atendimento', header: 'Tempo Dens.' },
          { field: 't', header: '% Tempo Restante' },
        ];
      }, error => {
        console.log(error);
      });
  }

}
