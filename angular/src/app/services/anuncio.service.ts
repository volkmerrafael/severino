import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { Anuncio } from "../model/anuncio";
import { environment } from "../../environments/environment";

@Injectable()
export class AnuncioService {

  constructor(private http: HttpClient) {}

  findAllByStatus(status: string): Observable<Anuncio[]> {
    return this.http.get<Anuncio[]>(
      `${environment.server}anuncio/findAnunciosByStatus?status=${status}`
    );
  }

}
