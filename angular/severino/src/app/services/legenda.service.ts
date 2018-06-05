import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { HttpHeaders } from '@angular/common/http';
import { Legenda } from '../shared/models/legenda';

@Injectable()
export class LegendaService {

  constructor(private http: HttpClient) {

   }

  consultaLegenda(ano: String, mes: String): Observable<Legenda[]> {
    return this.http.get<Legenda[]>(
      `${environment.server}legenda/listar/` + ano + `/` + mes
    );
  }
}
