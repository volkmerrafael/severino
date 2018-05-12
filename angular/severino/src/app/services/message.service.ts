import { Injectable } from "@angular/core";
import { Subject } from "rxjs/Subject";
import { Observable } from "rxjs/Observable";

@Injectable()
export class MessageService {

  messageObservable = new Subject<{}>();

  onMessage(): Observable<{}> {
    return this.messageObservable.asObservable();
  }

  messageSuccess(message: string) {
    console.log(message);
    this.message(message, 'success');
  }

  messageError(message: string) {
    console.log(message);
    this.message(message, 'error');
  }

  message(texto: string, type: string) {
    this.messageObservable.next( {message: texto, type: type} );
  }

}


