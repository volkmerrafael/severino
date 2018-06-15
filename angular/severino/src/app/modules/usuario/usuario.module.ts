import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { QuillModule } from 'ngx-quill';
import { ButtonModule } from 'primeng/button';
import { EditorModule } from 'primeng/editor';

import { DeclaracaoComponent } from './pages/declaracao/declaracao.component';
import { UsuarioService } from './usuario.service';

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
