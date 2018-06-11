import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../../environments/environment";
import { catchError, tap  } from 'rxjs/operators';
import { PrioridadeJira } from "../../shared/models/prioridadeJira";

@Injectable()
export class JiraService {

  constructor(private http: HttpClient) {}

  listaPrioridadeJira(): Observable<PrioridadeJira[]> {
    return this.http.get<PrioridadeJira[]>(
      `${environment.server}jira/prioridade/listar`
    );
  }
}
