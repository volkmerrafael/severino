import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginModule } from './pages/login/login.module';
import { AppRoutingModule } from './app-routing.module';
import { CadastroModule } from './pages/cadastro/cadastro.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MessageComponent } from './components/message/message.component';
import { MessageService } from './services/message.service';
import { PontoComponent } from './pages/ponto/ponto.component';
import { TableModule } from 'primeng/table';
import { HttpModule } from '@angular/http';
import { PontoService } from './services/ponto.service';
import { UserHttpInterceptor } from './auth/user-http.interceptor';

export const HttpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: UserHttpInterceptor, multi: true  }
];

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    PontoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    LoginModule,
    CadastroModule,
    AppRoutingModule,
    TableModule
  ],
  providers: [
    MessageService,
    PontoService,
    HttpInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
