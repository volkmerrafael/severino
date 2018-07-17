import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { PrioridadeInfo } from '../../shared/models/prioridadeInfo';

@Injectable()
export class JiraService {

  constructor(private http: HttpClient) {}

  listaPrioridadeJira(): Observable<PrioridadeInfo> {
    return this.http.get<PrioridadeInfo>(
      `${environment.server}jira/prioridade/listar`
    );
  }

}
