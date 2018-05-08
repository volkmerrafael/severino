import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { Ponto } from "../model/ponto";
import { environment } from "../../environments/environment";

@Injectable()
export class AnuncioService {

  constructor(private http: HttpClient) {}

  findAllByStatus(status: string): Observable<Ponto[]> {
    return this.http.get<Ponto[]>(
      `${environment.server}ponto/listar/2018/04`
    );
  }

}
