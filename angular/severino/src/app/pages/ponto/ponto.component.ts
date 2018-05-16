import { AnoMes } from './../../model/anomes';
import { Component, OnInit } from '@angular/core';
import { PontoService } from '../../services/ponto.service';
import { Ponto } from '../../model/ponto';
import { ArquivoImportacao } from "../../model/arquivoimportacao";
import { FileUploadModule } from 'primeng/fileupload';
import { Importacao } from "../../model/importacao";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { Usuario } from '../../model/usuario';
import { DateFormatPipe } from '../../components/pipes/pipe'
import { DropdownModule } from 'primeng/dropdown';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';

@Component({
  selector: 'app-ponto',
  templateUrl: './ponto.component.html',
  styleUrls: ['./ponto.component.css']
})
export class PontoComponent implements OnInit {

  mes : String = "02";
  ano : String = "2018";
  pontos : Ponto[] = [];
  periodos : AnoMes[] = [];
  periodoSelecionado : AnoMes;
  usuario: Usuario = new Usuario();
  importacao: Importacao = new Importacao(); 
  arqImportacao: ArquivoImportacao = new ArquivoImportacao();  

  constructor(private pontoService: PontoService) {

    this.usuario.nome = sessionStorage.getItem('nomeUsuario');

    this.pontoService
                .listarPontoPorPeriodo(this.ano, this.mes)
                .subscribe( res => {
                  this.pontos = res 
                  console.log(res);
                 });        
                 
    this.pontoService
                .listarPeriodos()
                .subscribe( res => {
                        this.periodos = res 
                        console.log(res);
                      });           
   }

  ngOnInit() {

  }

  listarPeriodos() {
    this.pontoService
                .listarPeriodos()
                .subscribe( res => {
                       this.periodos = res 
                       console.log(res);
                      });
  }

  listarPontoPorPeriodo() {
    this.pontoService
                .listarPontoPorPeriodo(this.mes, this.ano)
                .subscribe( res => {
                       this.pontos = res 
                       console.log(res);
                      });
  }  
}
