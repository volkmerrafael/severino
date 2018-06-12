import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from './usuario.service';
import { DeclaracaoComponent } from './pages/declaracao/declaracao.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [DeclaracaoComponent],
  providers: [
    UsuarioService
  ]
})
export class UsuarioModule { }
