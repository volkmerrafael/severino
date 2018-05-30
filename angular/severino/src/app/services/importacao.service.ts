import { AnoMes } from './../shared/model/anomes';
import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable } from "rxjs/Observable";
import { environment } from "../../environments/environment";
import { Ponto } from "../shared/model/ponto";
import { TableModule } from 'primeng/table';
import { Importacao } from "../shared/model/importacao";
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
