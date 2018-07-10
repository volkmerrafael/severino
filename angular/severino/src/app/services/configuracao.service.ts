import { Injectable } from '@angular/core';
import { Conexao } from '../shared/models/conexao';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConfiguracaoService {

  constructor(private http: HttpClient) { }

  cadastro( conexao: Conexao ): Observable<Conexao> {
    return this.http.post<Conexao>(
      `${environment.server}usuario`, conexao
    );
  }
}
