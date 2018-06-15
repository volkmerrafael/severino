import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Usuario1Service } from './usuario.service';
import { DeclaracaoComponent } from './pages/declaracao/declaracao.component';
import { EditorModule } from 'primeng/editor';
import { QuillModule } from 'ngx-quill';
import { ReactiveFormsModule } from '@angular/forms';
import {ButtonModule} from 'primeng/button';
import { ConfiguracaoComponent } from './pages/configuracao/configuracao.component';
import {CheckboxModule} from 'primeng/checkbox';
import {PanelModule} from 'primeng/panel';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    EditorModule,
    QuillModule,
    ReactiveFormsModule,
    ButtonModule,
    CheckboxModule,
    PanelModule,
    FormsModule
  ],
  declarations: [DeclaracaoComponent, ConfiguracaoComponent],
  exports: [DeclaracaoComponent],
  providers: [Usuario1Service]
})
export class UsuarioModule { }
