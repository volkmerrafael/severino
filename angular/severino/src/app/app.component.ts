import { Component } from '@angular/core';
import { MessageService } from 'primeng/components/common/messageservice';
import {Message} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [MessageService]
})
export class AppComponent {

  msgs: Message[] = [];

}
