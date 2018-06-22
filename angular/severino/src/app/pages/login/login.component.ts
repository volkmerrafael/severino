import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioService } from "../../services/usuario.service";
import { Login } from "../../shared/models/login";
import { Acesso } from '../../shared/models/acesso';
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

  usuario: Login;
  msgs: Message[] = [];
  mensagemGrow;
  tituloGrow;
  tipoGrow;
  id: any;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private messageService: MessageService,
    private tratamentoErrosService: TratamentoErrosService,
  ) {
    this.usuario = <Login>{};
  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  login() {
    if (this.usuario.nomeacesso === undefined) {
    this.tipoGrow = "error";
    this.tituloGrow = 'Ops';
    this.mensagemGrow = "Preencha o campo nome";
    this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
  } else if (this.usuario.senha === undefined) {
      this.tipoGrow = "error";
      this.tituloGrow = 'Ops';
      this.mensagemGrow = "Preencha o campo senha";
      this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    } else {
    this.usuarioService.login(this.usuario)
    .subscribe(res => {
      sessionStorage.setItem('id', '' + res.usuario.id);
      sessionStorage.setItem('nomeUsuario', res.usuario.nome);
      sessionStorage.setItem('emailUsuario', res.usuario.email);
      sessionStorage.setItem('usertoken', res.usertoken);
      sessionStorage.setItem('sessaotoken', res.sessaotoken);
      sessionStorage.setItem('nomeacesso', res.nomeacesso);
      sessionStorage.setItem('tipo', res.usuario.acesso.tipo);
      this.usuarioService.usuarioJira()
      .subscribe(res1 => {
        sessionStorage.setItem('usuarioJira', '' + res1.integra_jira);
      });
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
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
    });
  }
  }

  logout(): void {
    sessionStorage.removeItem('usuarioJira');
    sessionStorage.removeItem('nomeUsuario');
    sessionStorage.removeItem('usertoken');
    sessionStorage.removeItem('sessaotoken');
    sessionStorage.removeItem('nomeacesso');
    sessionStorage.removeItem('id');
    this.id = sessionStorage.getItem('id');
  }

}
