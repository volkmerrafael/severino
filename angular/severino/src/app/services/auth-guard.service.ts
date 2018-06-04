import { Injectable } from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanActivateChild,
  NavigationExtras,
  CanLoad, Route
} from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';

@Injectable()
export class AuthGuardService implements CanActivate, CanActivateChild, CanLoad {

  perfilAdmin: any = false;
  arrayGrow: Array<any> = new Array<any>();
  tipoUsuario: any;
  mensagemGrow;
  tituloGrow;
  tipoGrow;
  url;

  constructor(
    private messageService: MessageService,
    private router: Router,
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    this.url = route.url;
    const url: string = state.url;
    if (url === '/admin') {
      return (this.checkLoginAdmin(url));
    } else {
      return (this.checkLogin(url));
    }
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.canActivate(route, state);
  }

  canLoad(route: Route): boolean {
    const url = `/${route.path}`;
    return this.checkLogin(url);
  }

  showGrowl(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  checkLogin(url: string): boolean {
     const token = sessionStorage.getItem('usertoken');
    if (token != null && token !== undefined && token !== '') {
      return (true);
    }
    this.tipoGrow = "error";
    this.tituloGrow = 'Erro';
    this.mensagemGrow = 'Você nao tem acesso a esse conteúdo.';
    this.showGrowl(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    return (false);
  }

  checkLoginAdmin(url: string): boolean {
    const token = sessionStorage.getItem('usertoken');
    this.tipoUsuario = sessionStorage.getItem('tipo');
    if (this.tipoUsuario === 'ADMIN') {
        this.perfilAdmin = true;
    }
    if (token != null && token !== undefined && token !== '' && this.perfilAdmin === true) {
      return (true);
    }
    this.tipoGrow = "error";
    this.tituloGrow = 'Erro';
    this.mensagemGrow = 'Você nao tem acesso a esse conteúdo.';
    this.showGrowl(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    this.logout();
    this.router.navigate(['/login']);
    return (false);
  }
  logout(): void {
    sessionStorage.removeItem('nomeUsuario');
    sessionStorage.removeItem('usertoken');
    sessionStorage.removeItem('sessaotoken');
    sessionStorage.removeItem('nomeacesso');
  }
}
