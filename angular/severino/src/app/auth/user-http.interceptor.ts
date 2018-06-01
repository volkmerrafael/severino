import {
  HttpRequest,
  HttpInterceptor,
  HttpHandler,
  HttpEvent,
  HttpHeaders
} from "@angular/common/http";

import { Injectable } from "@angular/core";

import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';

@Injectable()

export class UserHttpInterceptor implements HttpInterceptor {



  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const urlReq = String(req.url);
    const urlLogin = urlReq.indexOf("login");
    const sessaotoken = sessionStorage.getItem('sessaotoken');
    const usertoken = sessionStorage.getItem('usertoken');
    const nomeacesso = sessionStorage.getItem('nomeacesso');

    if (urlLogin === -1) {
      if (usertoken) {
        const secureReq = req.clone({
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Session-Token': sessaotoken,
            'User-Token': usertoken
          })
        });
        return next.handle(secureReq);
      }
    } else {
      return next.handle(req);
    }
  }
}
