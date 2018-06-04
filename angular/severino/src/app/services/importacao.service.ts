import { AnoMes } from './../shared/models/anomes';
import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { Ponto } from "../shared/models/ponto";
import { TableModule } from 'primeng/table';
import { Importacao } from "../shared/models/importacao";
import { HttpHeaders } from '@angular/common/http';
@Injectable()

export class ImportacaoService {

  constructor(private http: HttpClient) { }

  uploadArquivo(importacao: Importacao): Observable<Importacao> {
    return this.http.post<Importacao>(
        `${environment.server}importacao/`, importacao);
  }

  listarImportacao(): Observable<Importacao[]> {
    return this.http.get<Importacao[]>(
      `${environment.server}importacao`
    );
  }
}
