import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginModule } from './pages/login/login.module';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TableModule } from 'primeng/table';
import { HttpModule } from '@angular/http';
import { PontoService } from './services/ponto.service';
import { UserHttpInterceptor } from './auth/user-http.interceptor';
import { FileUploadModule } from 'primeng/fileupload';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { DateFormatPipe } from './components/pipes/pipe';
import { DropdownModule } from 'primeng/dropdown';
import { AdminComponent } from './pages/admin/admin.component';
import { ImportacaoService } from './services/importacao.service';
import { PontoModule } from './pages/ponto/ponto.module';
import { AdminModule } from './pages/admin/admin.module';
import {GrowlModule} from 'primeng/growl';
import { MessageService } from 'primeng/components/common/messageservice';
import { TratamentoErrosService } from "./services/tratamento-erros.service";
import { MessageComponent } from './components/message/message.component';
import { HomeComponent } from './pages/home/home.component';
import { ButtonModule } from 'primeng/button';
import { AuthGuardService } from './services/auth-guard.service'

export const HttpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: UserHttpInterceptor, multi: true  }
];

@NgModule({
  declarations: [
    AppComponent,
    DateFormatPipe,
    MessageComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    LoginModule,
    AppRoutingModule,
    TableModule,
    FileUploadModule,
    MenubarModule,
    DropdownModule,
    PontoModule,
    AdminModule,
    GrowlModule,
    ButtonModule
  ],
  providers: [
    HttpInterceptorProviders,
    ImportacaoService,
    MessageService,
    TratamentoErrosService,
    AuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
