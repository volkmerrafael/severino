import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { JiraService } from './jira.service';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { FormsModule } from "@angular/forms";
import { Message } from 'primeng/components/common/message';

@NgModule({
  imports: [
    CommonModule,
    TableModule,
    FormsModule
  ],
  declarations: [
    DashboardComponent
  ],
  exports: [
    DashboardComponent
  ],
  providers: [
    JiraService
  ]
})
export class JiraModule { }
