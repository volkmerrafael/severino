import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { environment } from "../../environments/environment";
import { Login } from "../shared/models/login";
import { Usuario } from "../shared/models/usuario";
import { catchError, tap  } from 'rxjs/operators';

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
      `${environment.server}usuario/public`, usuario
    );
  }

  obterUsuario(): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${environment.server}usuario/obterDadosUsuario`
    );
  }

  usuario(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${environment.server}/severino/rest/usuario/` + id
    );
  }


}
