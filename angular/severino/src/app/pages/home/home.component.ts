import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../shared/models/usuario';
import { Router, NavigationStart, NavigationEnd, NavigationError, NavigationCancel, Event, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MenuItem } from 'primeng/api';
import { UsuarioService } from '../../services/usuario.service';

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
  logado: any = false;
  idUsuarioLogado: any;
  listaId: any;
  idAny: any;
  id: number;

  constructor(
    private router: Router,
  ) {
    this.routerEventsSubscription = this.router.events.subscribe((event: Event) => this.verificarEventosDeRota(event));
  }

  ngOnInit() {
    this.items = [
      {
        label: 'Perfil', icon: 'fa-user-circle', command: (event) => {
          event.originalEvent = this.consultarUsuario(parseInt(sessionStorage.getItem('id'), 10));
        }
      },
      {
        label: 'Editar perfil', icon: 'fa fa-fw fa-edit', command: (event) => {
          event.originalEvent = this.editarUsuario(parseInt(sessionStorage.getItem('id'), 10));
        }
      },
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
    sessionStorage.removeItem('id');
    this.router.navigate(['/login']);
  }

  onClickNavigator(rota: String) {
    if (rota === 'admin') {
      this.router.navigate(['/admin']);
    } else if (rota === 'lista') {
      this.router.navigate(['/lista']);
    } else if (rota === 'ponto') {
      this.router.navigate(['/ponto']);
    } else if (rota === 'jira') {
      this.router.navigate(['/jira']);
    } else if (rota === 'admin/configuracao') {
      this.router.navigate(['/admin/configuracao']);
    } else if (rota === 'feedback') {
      this.router.navigate(['/feedback']);
    }

  }

  consultarUsuario(id: any) {
    this.router.navigate(['/perfil'], { queryParams: { id } });
  }
  editarUsuario(id: any) {
    this.router.navigate(['/editar-perfil'], { queryParams: { id } });
  }
  feedback() {
    this.router.navigate(['/feedback']);
  }
}
