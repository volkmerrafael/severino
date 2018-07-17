import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Login } from '../../shared/models/login';
import { UsuarioService } from '../../services/usuario.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { Router, ActivatedRoute } from '@angular/router';
import { Evento } from '../../shared/models/evento';

@Component({
  selector: 'app-editar-evento',
  templateUrl: './editar-evento.component.html',
  styleUrls: ['./editar-evento.component.css']
})
export class EditarEventoComponent implements OnInit {

  perfilForm: FormGroup;
  evento: Evento = new Evento();
  myGroup: FormGroup;
  login: Login;
  id: number;
  mensagemGrow;
  tituloGrow;
  tipoGrow;
  listaId: any;
  idAny: any;
  admin: boolean;
  results: string[];
  texto: string;
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
    this.admin = false;
    this.listaId = this.route.queryParams;
    this.idAny = this.listaId.value;
    this.id = parseInt((this.idAny.id), 10);
    if (this.id !== 0) {
      this.buscaPerfil();
    }
    this.perfilForm = this.fb.group({
      'inputDescricao': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputDataHoraInicio': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputDataHoraTermino': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputDataHoraLimite': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      'inputLimiteVagas': new FormControl('', Validators.compose([Validators.required, Validators.minLength(1)])),
      'inputMentor': new FormControl('', Validators.compose([Validators.required, Validators.minLength(1)])),
      'inputTipoEvento': new FormControl('', Validators.compose([Validators.required, Validators.minLength(1)])),
      'inputPermiteConvidados': new FormControl('', Validators.compose([Validators.required, Validators.minLength(1)])),
      'inputConfirmados': new FormControl('', Validators.compose([Validators.required, Validators.minLength(1)])),
      'inputDuracao': new FormControl('', Validators.compose([Validators.required, Validators.minLength(1)])),
      'inputFavorito': new FormControl('', Validators.compose([Validators.required, Validators.minLength(1)])),
    });
    if (sessionStorage.getItem('tipo') === 'ADMIN') {
      this.admin = true;
    }
  }

  buscaPerfil() {
    this.usuarioService.evento(this.id)
      .subscribe(res => {
        this.evento = res;
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
    if (this.id !== 0) {
      console.log(this.evento);
      this.usuarioService.editarevento(this.evento)
        .subscribe(res => {
          this.tipoGrow = "success";
          this.tituloGrow = 'Sucesso';
          this.mensagemGrow = "Evento atualizado";
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
      this.evento.id = undefined;
      this.usuarioService.cadastroevento(this.evento)
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

}

