import { HttpRequest,
         HttpInterceptor,
         HttpHandler,
         HttpEvent,
         HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable";

@Injectable()
export class AdminHttpInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const sessaotoken = localStorage.getItem('sessaotoken');
    const usertoken = localStorage.getItem('usertoken');
    const nomeacesso = localStorage.getItem('nomeacesso');

    if ( usertoken ) {
      const secureReq = req.clone({
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          'User-Agent': nomeacesso,
          'Session-Token' : sessaotoken,
          'User-Token': usertoken
        })
      });
      return next.handle(secureReq);
    }
    return next.handle(req);
  }
}
