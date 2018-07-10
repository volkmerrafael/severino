import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { environment } from "../../environments/environment";
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
