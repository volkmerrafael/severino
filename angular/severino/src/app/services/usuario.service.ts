import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { Login } from "../shared/models/login";
import { Usuario } from "../shared/models/usuario";
import { ConfiguracaoIntegracao } from '../shared/models/configuracaoIntegracao';
import { Departamento } from "../shared/models/departamento";
import { Funcao } from "../shared/models/funcao";
import { Empresa } from "../shared/models/empresa";
import { TipoEvento } from "../shared/models/tipoevento";
import { Evento } from "../shared/models/evento";

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

  cadastrotipoevento( tipoevento: TipoEvento ): Observable<TipoEvento> {
    return this.http.post<TipoEvento>(
      `${environment.server}tipoevento`, tipoevento
    );
  }

  cadastroevento( evento: Evento ): Observable<Evento> {
    return this.http.post<Evento>(
      `${environment.server}evento`, evento
    );
  }

  listaUsuarios(): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${environment.server}usuario`
    );
  }

  listaUsuariosPorDp(): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${environment.server}usuario/listar`
    );
  }

  usuario(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${environment.server}usuario/` + id
    );
  }

  tipoevento(id: number): Observable<TipoEvento> {
    return this.http.get<TipoEvento>(
      `${environment.server}tipoevento/` + id
    );
  }

  editar( usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(
      `${environment.server}usuario`, usuario
    );
  }

  editartipoevento( tipoevento: TipoEvento): Observable<TipoEvento> {
    return this.http.put<TipoEvento>(
      `${environment.server}tipoevento`, tipoevento
    );
  }

  evento(id: number): Observable<Evento> {
    return this.http.get<Evento>(
      `${environment.server}evento/` + id
    );
  }

  editarevento( evento: Evento): Observable<Evento> {
    return this.http.put<Evento>(
      `${environment.server}evento`, evento
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

  tiposevento(): Observable<TipoEvento[]> {
    return this.http.get<TipoEvento[]>(
      `${environment.server}tipoevento`
    );
  }

  eventos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(
      `${environment.server}evento`
    );
  }

  funcoes(): Observable<Funcao[]> {
    return this.http.get<Funcao[]>(
      `${environment.server}funcao`
    );
  }

  empresas(): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(
      `${environment.server}empresa`
    );
  }

}
