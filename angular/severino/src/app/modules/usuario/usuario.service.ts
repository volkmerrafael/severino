import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfiguracaoIntegracao } from '../../shared/models/configuracaoIntegracao';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Usuario1Service {

  constructor(
    private http: HttpClient
  ) { }

  getConfiguracao(): Observable<ConfiguracaoIntegracao[]> {
    return this.http.get<ConfiguracaoIntegracao[]>(
      `${environment.server}configuracao`
    );
  }

  putConfiguracao(configuracao: ConfiguracaoIntegracao): Observable<ConfiguracaoIntegracao[]> {
    return this.http.put<ConfiguracaoIntegracao[]>(
      `${environment.server}configuracao`, configuracao
    );
  }
}
