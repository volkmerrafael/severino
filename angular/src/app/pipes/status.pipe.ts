import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
 name: 'status'
})
export class StatusPipe implements PipeTransform {

  transform(value: string): string {

   if ( value === 'P' ) {
     return "Publicado";
   } else if ( value === 'A' ) {
     return "Aguardando publicação";
   } else if ( value === 'E' ) {
     return "Expirado";
   } else if ( value === 'D' ) {
     return "Despublicado";
   }
   return "-";
 }

}
