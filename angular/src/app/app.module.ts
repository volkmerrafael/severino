import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginModule } from './pages/login/login.module';
import { AppRoutingModule } from './app-routing.module';
import { CadastroModule } from './pages/cadastro/cadastro.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AdminModule } from './pages/admin/admin.module';
import { AdminHttpInterceptor } from './admin-http.interceptor';
import { MessageComponent } from './components/message/message.component';
import { MessageService } from './services/message.service';
import { PainelGeralComponent } from './pages/painel-geral/painel-geral.component';

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AdminHttpInterceptor, multi: true }
];

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    PainelGeralComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    LoginModule,
    CadastroModule,
    AppRoutingModule
  ],
  providers: [
    httpInterceptorProviders,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
