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
import { Empresa } from '../../shared/models/empresa';

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
  empresa: Empresa = new Empresa();
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
  empresas: Empresa[] = [];
  departamentos: Departamento[] = [];
  funcoes: Funcao[] = [];
  tipos: string[] = ['Administrador', 'Coordenador', 'Colaborador', 'Importador'];
  results: string[];
  texto: string;
  filteredEmpresas: any[];
  filteredDepartamentos: any[];
  filteredFuncoes: any[];
  filteredTipos: any[];
  pt: any;
  dataAdmissao: any;
  submitted: boolean;
  dataFormatada: any;

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
      monthNames: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
        "Novembro", "Dezembro"],
      monthNamesShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
      today: 'Hoje',
      clear: 'Clear'
    };
    this.listaEmpresas();
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
      'inputCPF': new FormControl('', [Validators.required]),
      'inputRG': new FormControl('', [Validators.required]),
      'inputTelefone': new FormControl('', [Validators.required]),
      'inputCelular': new FormControl('', [Validators.required]),
      'inputNomeAcesso': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputUsuarioJira': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputEmail': new FormControl('', Validators.compose([Validators.required, Validators.email])),
      'inputDataAdmissao': new FormControl('', [Validators.required]),
      'inputEmpresa': new FormControl('', [Validators.required]),
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
        this.acesso = this.usuario.acesso;
        this.senha = "";
        if (this.admin) {
          this.dataAdmissao = moment(this.usuario.data_admissao).format('DD/MM/YYYY');
          this.dataFormatada = moment(this.usuario.data_admissao).format('DD/MM/YYYY');
          if (this.usuario.empresa) {
            this.empresa = this.usuario.empresa;
          }
          if (this.usuario.departamento) {
            this.departamento = this.usuario.departamento;
          }
          if (this.usuario.funcao) {
            this.funcao = this.usuario.funcao;
          }
        }
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
    if (this.admin === true) {
      if (this.dataAdmissao !== this.dataFormatada) {
      this.usuario.data_admissao = moment(this.dataAdmissao).format('YYYY-MM-DD');
      }
      this.empresas.forEach(res => {
        if (res.razao_social === this.empresa.razao_social) {
          this.empresa.id = res.id;
          this.empresa.razao_social = res.razao_social;
        }
      });
      this.usuario.empresa = this.empresa;
      this.departamentos.forEach(res => {
        if (res.nome === this.departamento.nome) {
          this.departamento.id = res.id;
          this.departamento.nome = res.nome;
        }
      });
      this.usuario.departamento = this.departamento;
      this.funcoes.forEach(res => {
        if (res.nome === this.funcao.nome) {
          this.funcao.id = res.id;
          this.funcao.nome = res.nome;
        }
      });
      this.usuario.funcao = this.funcao;
      switch (this.acesso.tipo) {
        case 'Administrador': this.acesso.tipo = 'ADMIN'; break;
        case 'Coordenador': this.acesso.tipo = 'COORDENADOR'; break;
        case 'Colaborador': this.acesso.tipo = 'NORMAL'; break;
        case 'Importador': this.acesso.tipo = 'IMPORTADOR'; break;
      }
    }
    if (this.id !== 0) {
      console.log(this.usuario);
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
          this.voltar();
        }, error => {
          console.log(error);
          this.tipoGrow = "error";
          this.tituloGrow = 'Ops';
          this.mensagemGrow = error.error;
          this.showGrow(this.tipoGrow, this.tituloGrow, this.mensagemGrow);
        });
    } else {
      this.usuario.id = undefined;
      this.usuarioService.cadastro(this.usuario)
        .subscribe(res => {
          this.voltar();
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

  listaEmpresas() {
    this.usuarioService.empresas()
      .subscribe(res => {
        this.empresas = res;
      });
  }

  listaDepartamentos() {
    this.usuarioService.departamentos()
      .subscribe(res => {
        this.departamentos = res;
      });
  }

  listaFuncoes() {
    this.usuarioService.funcoes()
      .subscribe(res => {
        this.funcoes = res;
      });
  }

  search(event) {
    this.filteredDepartamentos = [];
    this.departamentos.forEach(res => {
      const brand = res.nome;
      if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
        this.filteredDepartamentos.push(brand);
      }
    });
  }

  searchFuncao(event) {
    this.filteredFuncoes = [];
    this.funcoes.forEach(res => {
      const brand = res.nome;
      if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
        this.filteredFuncoes.push(brand);
      }
    });
  }

  searchTipo(event) {
    this.filteredTipos = [];
    this.tipos.forEach(res => {
      const brand = res;
      if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
        this.filteredTipos.push(brand);
      }
    });
  }

  searchEmpresa(event) {
    this.filteredEmpresas = [];
    this.empresas.forEach(res => {
      const brand = res.razao_social;
      if (brand.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
        this.filteredEmpresas.push(brand);
      }
    });
  }

}

