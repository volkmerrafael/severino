import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable } from "rxjs/Observable";
import { environment } from "../../environments/environment";
import { Ponto } from "../model/ponto";
import { TableModule } from 'primeng/table';

@Injectable()
export class PontoService {

  constructor(private http: HttpClient) { }

  listarPontoPorPeriodo(ano: String, mes: String): Observable<Ponto[]> {
    console.log(ano);
    console.log(mes);
    return this.http.get<Ponto[]>(
      `${environment.server}ponto/listar/`+ano+`/`+mes
    );
  }
}
