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
      this.menuLateral.push({
        label: 'Geral',
        items: [{
          label: 'Empresa', icon: 'fa fa-user', command: (event) => {
            event.originalEvent = this.onClickNavigator('lista/empresa');
          }
        },
        {
          label: 'Departamento', icon: 'fa fa-user', command: (event) => {
            event.originalEvent = this.onClickNavigator('lista/departamento');
          }
        },
        {
          label: 'Função', icon: 'fa fa-user', command: (event) => {
            event.originalEvent = this.onClickNavigator('lista/funcao');
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

      this.menuLateral.push({
        label: 'Eventos',
        items: [{
          label: 'Tipo de Evento', icon: 'fa fa-user', command: (event) => {
            event.originalEvent = this.onClickNavigator('lista/tipoevento');
          }
        },
        {
          label: 'Evento', icon: 'fa fa-user', command: (event) => {
            event.originalEvent = this.onClickNavigator('lista/evento');
          }
        },
        ]
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
        },
        {
          label: 'Mapa', icon: 'fa fa-upload', command: (event) => {
            event.originalEvent = this.onClickNavigator('mapa');
          }
        }
        ]
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
    } else if (rota === 'lista/tipoevento') {
      this.router.navigate(['/lista/tipoevento']);
    } else if (rota === 'lista/funcao') {
      this.router.navigate(['/lista/funcao']);
    } else if (rota === 'lista/departamento') {
      this.router.navigate(['/lista/departamento']);
    } else if (rota === 'lista/empresa') {
      this.router.navigate(['/lista/empresa']);
    } else if (rota === 'lista/evento') {
      this.router.navigate(['/lista/evento']);
    } else if (rota === 'mapa') {
      this.router.navigate(['/mapa']);
    }

  }

  consultarUsuario(id: any) {
    this.router.navigate(['/perfil'], { queryParams: { id } });
  }
  editarUsuario(id: any) {
    this.router.navigate(['/editar-perfil'], { queryParams: { id } });
  }
  editarTipoEvento(id: any) {
    this.router.navigate(['/editar-tipo-evento'], { queryParams: { id } });
  }
  editarEvento(id: any) {
    this.router.navigate(['/editar-evento'], { queryParams: { id } });
  }
  editarFuncao(id: any) {
    this.router.navigate(['/editar-funcao'], { queryParams: { id } });
  }
  editarDepartamento(id: any) {
    this.router.navigate(['/editar-departamento'], { queryParams: { id } });
  }
  editarEmpresa(id: any) {
    this.router.navigate(['/editar-empresa'], { queryParams: { id } });
  }
  feedback() {
    this.router.navigate(['/feedback']);
  }
}
