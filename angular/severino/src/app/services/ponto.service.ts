import { AnoMes } from './../model/anomes';
import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable } from "rxjs/Observable";
import { environment } from "../../environments/environment";
import { Ponto } from "../model/ponto";
import { TableModule } from 'primeng/table';
import { Importacao } from "../model/importacao";
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class PontoService {
  
  constructor(private http: HttpClient) {
    
   }

  listarPontoPorPeriodo(ano: String, mes: String): Observable<Ponto[]> {
    return this.http.get<Ponto[]>(
      `${environment.server}ponto/listar/`+ano+`/`+mes
    );
  }

  listarPeriodos(): Observable<AnoMes[]> {
    return this.http.get<AnoMes[]>(
      `${environment.server}ponto/listar/periodos`
    );
  }
}
