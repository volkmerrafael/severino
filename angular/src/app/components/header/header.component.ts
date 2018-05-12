import { Component, Input } from "@angular/core";
import { Usuario } from "../../model/usuario";
import {ToolbarModule} from 'primeng/toolbar';

@Component({
  selector: "app-header",
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  @Input() usuario: Usuario;

}
