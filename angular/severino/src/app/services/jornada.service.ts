import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { HttpHeaders } from '@angular/common/http';
import { Jornada } from '../shared/models/jornada';

@Injectable()
export class JornadaService {

  constructor(private http: HttpClient) {

   }

  consultaJornada(ano: String, mes: String): Observable<Jornada[]> {
    return this.http.get<Jornada[]>(
      `${environment.server}jornada/listar/` + ano + `/` + mes
    );
  }
}
