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
export class PontoService {

  constructor(private http: HttpClient) {

   }

  listarPontoPorPeriodo(usuario: number, ano: String, mes: String): Observable<Ponto[]> {
    return this.http.get<Ponto[]>(
      `${environment.server}ponto/listar/` + usuario + `/` + ano + `/` + mes
    );
  }

  listarPeriodos(): Observable<AnoMes[]> {
    return this.http.get<AnoMes[]>(
      `${environment.server}ponto/listar/periodos`
    );
  }

  ponto(): Observable<Ponto[]> {
    return this.http.get<Ponto[]>(
      `${environment.server}ponto`
    );
  }

  alterarPonto(ponto: Ponto): Observable<Ponto> {
    return this.http.put<Ponto>(
      `${environment.server}ponto`, ponto
    );
  }

}
