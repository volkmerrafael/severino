import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { Justificativa } from "../shared/models/justificativa";

@Injectable()
export class JustificativaService {

  constructor(private http: HttpClient) {}

  justificar(justificativa: Justificativa): Observable<Justificativa> {
    return this.http.post(
      `${environment.server}justificativa`,  justificativa,  {responseType: 'json'}
    ).pipe<Justificativa>(
    );
  }

  editar(justificativa: Justificativa): Observable<Justificativa> {
    return this.http.put<Justificativa>(
      `${environment.server}justificativa`, justificativa
    );
  }

  justificativas(data: string): Observable<Justificativa> {
    return this.http.get<Justificativa>(
      `${environment.server}justificativa/` + data
    );
  }

  listaJustificativasNoMes(usuario: number, ano: string, mes: string): Observable<Justificativa[]> {
    return this.http.get<Justificativa[]>(
      `${environment.server}justificativa/listar/` + usuario + `/` + ano + `/` + mes
    );
  }

}
