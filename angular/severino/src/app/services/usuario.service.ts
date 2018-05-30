import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs/Observable";
import { environment } from "../../environments/environment";
import { Login } from "../shared/model/login";
import { Usuario } from "../shared/model/usuario";
import { catchError } from 'rxjs/operators';

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

}
