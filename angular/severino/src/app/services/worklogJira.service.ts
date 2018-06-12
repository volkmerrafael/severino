import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { HttpHeaders } from '@angular/common/http';
import { WorklogJira } from '../shared/models/worklogJira';

@Injectable()
export class WorklogJiraService {

  constructor(private http: HttpClient) {
    }
    listarIssues(usuario: number, data: string) {
        return this.http.get<[WorklogJira]>(
          `${environment.server}jira/worklog/listar/worklog/` + usuario + `/` + data
        );
      }
}
