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

    var urlLogin = urlReq.indexOf("login");

    const sessaotoken = sessionStorage.getItem('sessaotoken');
    const usertoken = sessionStorage.getItem('usertoken');
    const nomeacesso = sessionStorage.getItem('nomeacesso');

    if (urlLogin == -1) {
      console.log("user-http.interceptor.ts");
      console.log("Não é  URL de Login (Entra no IF)");
      if ( usertoken ) {
        console.log("Criou Header");
        const secureReq = req.clone({
          headers: new HttpHeaders({
            'Content-Type' : 'application/json',
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
