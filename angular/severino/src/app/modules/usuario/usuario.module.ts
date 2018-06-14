import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from './usuario.service';
import { DeclaracaoComponent } from './pages/declaracao/declaracao.component';
import { EditorModule } from 'primeng/editor';
import { QuillModule } from 'ngx-quill';
import { ReactiveFormsModule } from '@angular/forms';
import {ButtonModule} from 'primeng/button';

@NgModule({
  imports: [
    CommonModule,
    EditorModule,
    QuillModule,
    ReactiveFormsModule,
    ButtonModule
  ],
  declarations: [DeclaracaoComponent],
  exports: [DeclaracaoComponent],
  providers: [UsuarioService]
})
export class UsuarioModule { }
