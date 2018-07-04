import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../shared/models/usuario';
import { Router, NavigationStart, NavigationEnd, NavigationError, NavigationCancel, Event, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MenuItem } from 'primeng/api';
import { Feedback } from '../../shared/models/feedback';

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
  menuLateral: MenuItem[];
  admin: any = false;
  logado: any = false;
  idUsuarioLogado: any;
  listaId: any;
  idAny: any;
  id: number;
  coordenador: any = false;
  tipo: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.routerEventsSubscription = this.router.events.subscribe((event: Event) => this.verificarEventosDeRota(event));
  }

  ngOnInit() {
    this.tipo = sessionStorage.getItem('tipo');
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
    this.tipo = sessionStorage.getItem('tipo');
    this.montaMenu();
    if (event instanceof NavigationEnd) {
      if (this.token !== '' && this.token !== undefined && this.token != null) {
        this.mostraLinks = true;
      } else {
        this.mostraLinks = false;
      }
    }
  }

  montaMenu() {
    this.menuLateral = [];
    if (this.tipo === 'ADMIN' || this.tipo === 'COORDENADOR') {
      this.menuLateral.push({
        label: 'Colaboradores',
        items: [{
          label: 'Lista', icon: 'fa fa-user', command: (event) => {
            event.originalEvent = this.onClickNavigator('lista');
          }
        },
        {
          label: 'Cadastro', icon: 'fa fa-fw fa-plus', command: (event) => {
            event.originalEvent = this.editarUsuario(0);
          }
        },
        ]
      }
      );
    }
    if (this.tipo === 'NORMAL') {
      this.menuLateral.push({
        label: '',
        items: [{
        label: 'Ponto', icon: 'fa fa-fw fa-file-o', command: (event) => {
          event.originalEvent = this.onClickNavigator('ponto');
        }
      }]
      }
      );
    }
    if (this.tipo === 'NORMAL' || this.tipo === 'ADMIN' || this.tipo === 'COORDENADOR' || this.tipo === 'IMPORTADOR') {
      this.menuLateral.push({
        label: '',
        items: [{
        label: 'Feedback', icon: 'fa fa-fw fa-edit', command: (event) => {
          event.originalEvent = this.onClickNavigator('feedback');
        }
      }]
      }
      );
    }
    if (this.tipo === 'ADMIN') {
      this.menuLateral.push({
        label: '',
        items: [{
        label: 'Configurações', icon: 'fa fa-cogs', command: (event) => {
          event.originalEvent = this.onClickNavigator('admin/configuracao');
        }
      },
        {
          label: 'Importações', icon: 'fa fa-upload', command: (event) => {
            event.originalEvent = this.onClickNavigator('admin');
          }
        }]
      });
    }
  }

  logout(): void {
    this.usuario = new Usuario();
    this.menuLateral = [];
    sessionStorage.removeItem('nomeUsuario');
    sessionStorage.removeItem('usertoken');
    sessionStorage.removeItem('sessaotoken');
    sessionStorage.removeItem('nomeacesso');
    sessionStorage.removeItem('tipo');
    this.token = '';
    this.tipo = '';
    sessionStorage.removeItem('id');
    this.router.navigate(['/login']);
  }

  onClickNavigator(rota: String) {
    if (rota === 'admin') {
      this.router.navigate(['/admin']);
    } else if (rota === 'ponto') {
      this.router.navigate(['/ponto']);
    } else if (rota === 'jira') {
      this.router.navigate(['/jira']);
    } else if (rota === 'admin/configuracao') {
      this.router.navigate(['/admin/configuracao']);
    } else if (rota === 'feedback') {
      this.router.navigate(['/feedback']);
    } else if (rota === 'lista') {
      this.router.navigate(['/lista']);
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
