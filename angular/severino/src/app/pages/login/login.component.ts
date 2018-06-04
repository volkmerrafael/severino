import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioService } from "../../services/usuario.service";
import { Login } from "../../shared/models/login";
import { Usuario } from '../../shared/models/usuario';
import { MessageService } from "primeng/components/common/messageservice";
import { TratamentoErrosService } from "../../services/tratamento-erros.service";
import { Message } from "../../shared/models/message";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  user: any;
  usuario: Login;
  msgs: Message[] = [];
  mensagemGrow;
  tituloGrow;
  tipoGrow;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private messageService: MessageService,
    private tratamentoErrosService: TratamentoErrosService,
  ) {
    this.usuario = <Login>{};
  }

  showSuccess(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  login() {
    this.usuarioService.login(this.usuario)
    .subscribe(res => {
      console.log(res);
      this.user = res;
      sessionStorage.setItem('user', this.user);
      sessionStorage.setItem('nomeUsuario', res.usuario.nome);
      sessionStorage.setItem('emailUsuario', res.usuario.email);
      sessionStorage.setItem('usertoken', res.usertoken);
      sessionStorage.setItem('sessaotoken', res.sessaotoken);
      sessionStorage.setItem('nomeacesso', res.nomeacesso);
      sessionStorage.setItem('tipo', res.usuario.acesso.tipo);

      if (res.usuario.acesso.tipo === "ADMIN") {
        this.router.navigate(['/admin']);
      } else {
        sessionStorage.setItem('departamentoUsuario', res.usuario.departamento.nome);
        sessionStorage.setItem('pisUsuario', res.usuario.pis);
        sessionStorage.setItem('funcaoUsuario', res.usuario.funcao.nome);
        sessionStorage.setItem('dataAdmissao', res.usuario.data_admissao);
        this.router.navigate(['/ponto']);
      }
    }, error => {
      this.tratamentoErrosService.handleError(error);
    });

  }

  logout(): void {
    sessionStorage.removeItem('nomeUsuario');
    sessionStorage.removeItem('usertoken');
    sessionStorage.removeItem('sessaotoken');
    sessionStorage.removeItem('nomeacesso');
  }

}
