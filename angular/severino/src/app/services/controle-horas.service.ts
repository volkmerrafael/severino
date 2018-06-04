import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { HttpHeaders } from '@angular/common/http';
import { ControleHoras } from '../shared/models/controle-horas';

@Injectable()
export class ControleHorasService {

  constructor(private http: HttpClient) {

   }

  controleHoras(ano: String, mes: String): Observable<ControleHoras> {
    return this.http.get<ControleHoras>(
      `${environment.server}controlehoras/listar/` + ano + `/` + mes
    );
  }
}
