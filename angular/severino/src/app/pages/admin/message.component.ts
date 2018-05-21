import { Component } from "@angular/core";
import { MessageService } from "../../services/message.service";

@Component({
 selector: 'app-message',
 templateUrl: './message.component.html',
 styleUrls: ['./message.component.css']
})
export class MessageComponent {

  message: string;

  constructor(private messageService: MessageService) {
    this.messageService.onMessage().subscribe((res: { message: string }) => {

      this.message = res.message;

      setTimeout( () => {
        this.fechar();
      }, 5000);
   });
 }

 fechar() {
   this.message = null;
 }
}
