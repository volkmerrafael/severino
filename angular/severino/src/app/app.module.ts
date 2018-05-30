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

export const HttpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: UserHttpInterceptor, multi: true  }
];

@NgModule({
  declarations: [
    AppComponent,
    DateFormatPipe
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
    AdminModule
  ],
  providers: [
    HttpInterceptorProviders,
    ImportacaoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
