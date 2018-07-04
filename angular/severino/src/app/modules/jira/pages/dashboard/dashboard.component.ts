import { Component, OnInit } from '@angular/core';
import { PrioridadeJira } from '../../../../shared/models/prioridadeJira';
import { MessageService } from 'primeng/components/common/messageservice';
import { JiraService } from '../../jira.service';
import * as moment from 'moment/moment';
import { PrioridadeInfo } from '../../../../shared/models/prioridadeInfo';

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
  uploadedFiles: any[] = [];
  cols: any[];
  prioridadeInfo: PrioridadeInfo = new PrioridadeInfo;
  prioridadesJira: PrioridadeJira[] = [];
  data: any;
  id: number;

  constructor(
    private jiraService: JiraService
  ) {
    this.id = parseInt(sessionStorage.getItem('id'), 10);
   }

  ngOnInit() {
    this.listarJiraPrioridade();
    this.carregarGraficos();
  }

  listarJiraPrioridade() {
    this.jiraService.listaPrioridadeJira(this.id)
      .subscribe(res => {
        this.prioridadesJira = res.lista_prioridades;
        this.prioridadesJira.forEach(data => {
        data.pendenteem = moment(data.pendenteem).format("DD/MM/YYYY");
        });
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
      });
  }

  carregarGraficos() {
    this.data = {
      labels: ['A', 'B', 'C'],
      datasets: [
          {
              data: [300, 50, 100],
              backgroundColor: [
                  "#FF6384",
                  "#36A2EB",
                  "#FFCE56"
              ],
              hoverBackgroundColor: [
                  "#FF6384",
                  "#36A2EB",
                  "#FFCE56"
              ]
          }]
      };
  }

}
