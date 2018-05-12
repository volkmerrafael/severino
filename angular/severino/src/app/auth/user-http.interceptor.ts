import { HttpRequest,
  HttpInterceptor,
  HttpHandler,
  HttpEvent,
  HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable"

@Injectable()
export class UserHttpInterceptor implements HttpInterceptor {

intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

var urlReq = new String(req.url);

var urlLogin = urlReq.indexOf("Login");

const sessaotoken = localStorage.getItem('sessaotoken');
const usertoken = localStorage.getItem('usertoken');
const nomeacesso = localStorage.getItem('nomeacesso');

if (urlLogin != -1) {
  if ( usertoken ) {
    const secureReq = req.clone({
     headers: new HttpHeaders({
       'Content-Type' : 'application/json',
       'User-Agent' : nomeacesso,
       'Session-Token' : sessaotoken,
       'User-Token' : usertoken
     })
    })
      return next.handle(secureReq);
  }
} else {
  return next.handle(req);
}

}
}
