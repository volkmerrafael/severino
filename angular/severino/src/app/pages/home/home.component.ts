import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../shared/models/usuario';
import { RouterLink } from '@angular/router/src/directives/router_link';
import { Router, NavigationStart, NavigationEnd, NavigationError, NavigationCancel, Event } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Navigation } from 'selenium-webdriver';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  usuario: Usuario = new Usuario();
  token: any;
  routerEventsSubscription: Subscription;
  mostraLinks: any = false;
  items: MenuItem[];
  admin: any = false;

  constructor(
    private router: Router,
  ) {
    this.routerEventsSubscription = this.router.events.subscribe((event: Event) => this.verificarEventosDeRota(event));
  }

  ngOnInit() {
    this.items = [
      { label: 'Perfil', icon: 'fa-user-circle', routerLink: ['/perfil'] },
      {label: 'Editar perfil', icon: 'fa fa-fw fa-edit', routerLink: ['/editar-perfil']},
      {
        label: 'Sair', icon: 'fa-sign-out', routerLink: ['/login'], command: (event) => {
          event.originalEvent = this.logout();
        }
      },
    ];
  }

  verificarEventosDeRota(event: Event) {
    this.usuario.nome = sessionStorage.getItem('nomeUsuario');
    this.token = sessionStorage.getItem("usertoken");
    if (event instanceof NavigationEnd) {
      if (this.token !== '' && this.token !== undefined && this.token != null) {
        this.mostraLinks = true;
      } else {
        this.mostraLinks = false;
      }
    }
    if (sessionStorage.getItem('tipo') === 'ADMIN') {
      this.admin = true;
    } else {
      this.admin = false;
    }
  }

  logout(): void {
    this.usuario = new Usuario();
    sessionStorage.removeItem('nomeUsuario');
    sessionStorage.removeItem('usertoken');
    sessionStorage.removeItem('sessaotoken');
    sessionStorage.removeItem('nomeacesso');
    sessionStorage.removeItem('tipo');
    this.token = '';
    this.router.navigate(['/login']);
  }

  onClickNavigator(rota: String) {
  if (rota === 'admin') {
    this.router.navigate(['/admin']);
  }
  }
}
