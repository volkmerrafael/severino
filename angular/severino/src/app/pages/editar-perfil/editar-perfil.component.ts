import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Usuario } from '../../shared/models/usuario';
import { Acesso } from '../../shared/models/acesso';
import { Login } from '../../shared/models/login';
import { UsuarioService } from '../../services/usuario.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { Router, ActivatedRoute } from '@angular/router';
import { Departamento } from '../../shared/models/departamento';
import { Funcao } from '../../shared/models/funcao';
import * as moment from 'moment/moment';

@Component({
  selector: 'app-editar-perfil',
  templateUrl: './editar-perfil.component.html',
  styleUrls: ['./editar-perfil.component.css']
})
export class EditarPerfilComponent implements OnInit {

  perfilForm: FormGroup;
  usuario: Usuario = new Usuario();
  acesso: Acesso = new Acesso();
  funcao: Funcao = new Funcao();
  departamento: Departamento = new Departamento();
  senha: string;
  myGroup: FormGroup;
  login: Login;
  id: number;
  mensagemGrow;
  tituloGrow;
  tipoGrow;
  listaId: any;
  idAny: any;
  admin: boolean;
  departamentos: Departamento[] = [];
  funcoes: Funcao[] = [];
  tipos: string[] = ['Administrador', 'Coordenador', 'Colaborador', 'Importador'];
  results: string[];
  texto: string;
  filteredDepartamentos: any[];
  filteredFuncoes: any[];
  filteredTipos: any[];
  pt: any;
  dataAdmissao: any;
  submitted: boolean;

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.pt = {
      firstDayOfWeek: 0,
      dayNames: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabado"],
      dayNamesShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
      dayNamesMin: ["Do", "Se", "Te", "Qa", "Qu", "Se", "Sa"],
      monthNames: [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
      "Novembro", "Dezembro" ],
      monthNamesShort: [ "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" ],
      today: 'Hoje',
      clear: 'Clear'
  };
    this.listaFuncoes();
    this.listaDepartamentos();
    this.admin = false;
    this.listaId = this.route.queryParams;
    this.idAny = this.listaId.value;
    this.id = parseInt((this.idAny.id), 10);
    if (this.id !== 0) {
    this.buscaPerfil();
    }
    this.perfilForm = this.fb.group({
      'inputNome': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputPIS': new FormControl('', [Validators.required]),
      'inputNomeAcesso': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputEmail': new FormControl('', Validators.compose([Validators.required, Validators.email])),
      'inputDataAdmissao': new FormControl('', [Validators.required]),
      'inputDepartamento': new FormControl('', [Validators.required]),
      'inputFuncao': new FormControl('', [Validators.required]),
      'inputTipoAcesso': new FormControl('', [Validators.required]),
      'inputSenha': new FormControl('', Validators.compose([Validators.required, Validators.minLength(6)])),
    });
    if (sessionStorage.getItem('tipo') === 'ADMIN') {
      this.admin = true;
    }
  }

  buscaPerfil() {
    this.usuarioService.usuario(this.id)
      .subscribe(res => {
        this.usuario = res;
        this.usuario.data_admissao = moment(this.usuario.data_admissao).format("DD/MM/YYYY");
        this.acesso = this.usuario.acesso;
        this.departamento = this.usuario.departamento;
        this.funcao = this.usuario.funcao;
        this.senha = "";
      }, error => {
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      });
  }

  onSubmit(value: string) {
    this.submitted = true;
}

  clickEditar() {
    this.acesso.senha = this.senha;
    this.usuario.acesso = this.acesso;
    this.dataAdmissao = moment(this.usuario.data_admissao).format();
    this.usuario.data_admissao = moment(this.dataAdmissao).format('YYYY-MM-DD');
    if (this.id !== 0) {
    this.usuarioService.editar(this.usuario)
      .subscribe(res => {
        if (this.admin === false) {
          sessionStorage.setItem('id', '' + res.id);
          sessionStorage.setItem('nomeUsuario', res.nome);
          sessionStorage.setItem('emailUsuario', res.email);
          this.acesso = res.acesso;
          sessionStorage.setItem('usertoken', this.acesso.token);
          sessionStorage.setItem('nomeacesso', this.acesso.nomeacesso);
        }
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
        this.mensagemGrow = "Perfil atualizado";
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      }, error => {
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      });
    } else {
      this.usuario.id = undefined;
      this.departamentos.forEach( res => {
        if (res.nome === this.departamento.nome) {
          this.departamento.id = res.id;
        }
      });
      this.usuario.departamento = this.departamento;
      this.funcoes.forEach( res => {
        if (res.nome === this.funcao.nome) {
          this.funcao.id = res.id;
        }
      });
      this.usuario.funcao = this.funcao;
      if (this.acesso.tipo === 'Administrador') {
        this.acesso.tipo = 'ADMIN';
      } else if (this.acesso.tipo === 'Coordenador') {
        this.acesso.tipo = 'COORDENADOR';
      } else if (this.acesso.tipo === 'Colaborador') {
        this.acesso.tipo = 'NORMAL';
      } else if (this.acesso.tipo === 'Importador') {
        this.acesso.tipo = 'IMPORTADOR ';
      }
       this.usuarioService.cadastro(this.usuario)
      .subscribe( res => {
        this.tipoGrow = "success";
        this.tituloGrow = 'Sucesso';
        this.mensagemGrow = "Perfil cadastrado";
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      }, error => {
        this.tipoGrow = "error";
        this.tituloGrow = 'Ops';
        this.mensagemGrow = error.error;
        this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
      });
    }
  }

  showGrow(tipo, titulo, mensagem) {
    this.messageService.add({ severity: tipo, summary: titulo, detail: mensagem });
  }

  voltar() {
    this.location.back();
  }

  listaDepartamentos() {
    this.usuarioService.departamentos()
    .subscribe( res => {
        this.departamentos = res;
    });
  }

  listaFuncoes() {
    this.usuarioService.funcoes()
    .subscribe( res => {
        this.funcoes = res;
    });
  }

  search(event) {
    this.filteredDepartamentos = [];
    this.departamentos.forEach( res => {
        const brand = res.nome;
        if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
            this.filteredDepartamentos.push(brand);
        }
    });
  }

  searchFuncao(event) {
    this.filteredFuncoes = [];
    this.funcoes.forEach( res => {
        const brand = res.nome;
        if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
            this.filteredFuncoes.push(brand);
        }
    });
  }

  searchTipo(event) {
    this.filteredTipos = [];
    this.tipos.forEach( res => {
        const brand = res;
        if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
            this.filteredTipos.push(brand);
        }
    });
  }

}

