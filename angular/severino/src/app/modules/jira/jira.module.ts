import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { JiraService } from './jira.service';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { FormsModule } from "@angular/forms";
import { ChartModule } from 'primeng/chart';

@NgModule({
  imports: [
    CommonModule,
    TableModule,
    FormsModule,
    ChartModule
  ],
  declarations: [
    DashboardComponent,
  ],
  exports: [
    DashboardComponent
  ],
  providers: [
    JiraService
  ]
})
export class JiraModule { }
