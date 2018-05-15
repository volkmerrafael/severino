import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginModule } from './pages/login/login.module';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MessageComponent } from './components/message/message.component';
import { MessageService } from './services/message.service';
import { PontoComponent } from './pages/ponto/ponto.component';
import { TableModule } from 'primeng/table';
import { HttpModule } from '@angular/http';
import { PontoService } from './services/ponto.service';
import { UserHttpInterceptor } from './auth/user-http.interceptor';
import { FileUploadModule } from 'primeng/fileupload';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { DateFormatPipe } from './components/pipes/pipe';

export const HttpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: UserHttpInterceptor, multi: true  }
];

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    PontoComponent,
    DateFormatPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    LoginModule,
    AppRoutingModule,
    TableModule,
    FileUploadModule,
    MenubarModule
  ],
  providers: [
    MessageService,
    PontoService,
    HttpInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
