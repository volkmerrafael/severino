import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { Login } from "../shared/models/login";
import { Usuario } from "../shared/models/usuario";
import { ConfiguracaoIntegracao } from '../shared/models/configuracaoIntegracao';
import { Departamento } from "../shared/models/departamento";
import { Funcao } from "../shared/models/funcao";

@Injectable()
export class UsuarioService {

  constructor(private http: HttpClient) {}

  existeNomeAcesso(nomeacesso: string): Observable<Boolean> {
    return this.http.get<Boolean>(
      `${environment.server}teste/existeNomeAcesso/` + nomeacesso
    );
  }

  login(login: Login): Observable<Login> {
    return this.http.post(
      `${environment.server}login`,  login,  {responseType: 'json'}
    ).pipe<Login>(
    );
  }

  cadastro( usuario: Usuario ): Observable<Usuario> {
    return this.http.post<Usuario>(
      `${environment.server}usuario`, usuario
    );
  }

  listaUsuarios(): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${environment.server}usuario`
    );
  }

  usuario(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${environment.server}usuario/` + id
    );
  }

  editar( usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(
      `${environment.server}usuario`, usuario
    );
  }

  usuarioJira(): Observable<ConfiguracaoIntegracao> {
    return this.http.get<ConfiguracaoIntegracao>(
      `${environment.server}configuracao`
    );
  }

  departamentos(): Observable<Departamento[]> {
    return this.http.get<Departamento[]>(
      `${environment.server}departamento`
    );
  }

  funcoes(): Observable<Funcao[]> {
    return this.http.get<Funcao[]>(
      `${environment.server}funcao`
    );
  }
}
