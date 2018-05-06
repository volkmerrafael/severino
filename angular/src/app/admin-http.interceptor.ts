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
    const token = localStorage.getItem('token');
    if ( token ) {
      const secureReq = req.clone({
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      });
      return next.handle(secureReq);
    }
    return next.handle(req);
  }
}
