import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router/src/directives/router_link';
import { Router, NavigationStart, NavigationEnd, NavigationError, NavigationCancel, Event } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Navigation } from 'selenium-webdriver';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  token: any;
  routerEventsSubscription: Subscription;
  mostraLinks: any = false;

  constructor(
    private router: Router,
  ) {
    this.routerEventsSubscription = this.router.events.subscribe((event: Event) => this.verificarEventosDeRota(event));
  }

  ngOnInit() {

  }

  logout(): void {
    sessionStorage.removeItem('nomeUsuario');
    sessionStorage.removeItem('usertoken');
    sessionStorage.removeItem('sessaotoken');
    sessionStorage.removeItem('nomeacesso');
    this.token = '';
    this.router.navigate(['/login']);
  }

  verificarEventosDeRota(event: Event) {
    this.token = sessionStorage.getItem("usertoken");
    if (event instanceof NavigationEnd) {
      if (this.token !== '' && this.token !== undefined && this.token != null) {
        this.mostraLinks = true;
      } else {
        this.mostraLinks = false;
      }
    }
  }
}
