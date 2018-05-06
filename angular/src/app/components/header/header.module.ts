import { NgModule } from "@angular/core";
import { HeaderComponent } from "./header.component";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  declarations: [
    HeaderComponent
  ],
  exports: [
    HeaderComponent
  ]
})
export class HeaderModule {

}
