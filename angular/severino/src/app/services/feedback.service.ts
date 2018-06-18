import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Feedback } from '../shared/models/feedback';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  constructor(private http: HttpClient) { }

  feedback(): Observable<Feedback> {
    return this.http.post<Feedback>(
      `${environment.server}questao`
    );
  }

  listaFeedbacks(): Observable<Feedback> {
    return this.http.get<Feedback>(
      `${environment.server}questao`
    );
  }

}
