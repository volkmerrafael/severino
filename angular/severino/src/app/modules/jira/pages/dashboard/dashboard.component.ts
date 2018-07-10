import { Component, OnInit } from '@angular/core';
import * as moment from 'moment/moment';

import { PrioridadeInfo } from '../../../../shared/models/prioridadeInfo';
import { PrioridadeJira } from '../../../../shared/models/prioridadeJira';
import { JiraService } from '../../jira.service';

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

  constructor(
    private jiraService: JiraService
  ) { }

  ngOnInit() {
    this.listarJiraPrioridade();
    this.carregarGraficos();
  }

  listarJiraPrioridade() {
    this.jiraService.listaPrioridadeJira()
      .subscribe(res => {
        console.log(res);
        this.prioridadesJira = res.lista_prioridades;
        console.log(this.prioridadesJira);
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
