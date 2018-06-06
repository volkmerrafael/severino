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
import { AuthGuardService } from './services/auth-guard.service';
import { PerfilComponent } from './pages/perfil/perfil.component';
import {PanelModule} from 'primeng/panel';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { EditarPerfilComponent } from './pages/editar-perfil/editar-perfil.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MenuModule} from 'primeng/menu';
import {TieredMenuModule} from 'primeng/tieredmenu';
import { ControleHorasService } from './services/controle-horas.service';
import { JornadaService } from './services/jornada.service';
import { LegendaService } from './services/legenda.service';
import { ListaComponent } from './pages/lista/lista.component';
import {CardModule} from 'primeng/card';


export const HttpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: UserHttpInterceptor, multi: true  }
];

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    HomeComponent,
    PerfilComponent,
    EditarPerfilComponent,
    ListaComponent
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
    ButtonModule,
    PanelModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MenuModule,
    TieredMenuModule,
    CardModule,
  ],
  providers: [
    HttpInterceptorProviders,
    ImportacaoService,
    MessageService,
    TratamentoErrosService,
    AuthGuardService,
    ControleHorasService,
    JornadaService,
    LegendaService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
