import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment/moment';

@Pipe({
  name: 'formatarData'
})

export class FormatarDataPipe implements PipeTransform {
  transform(value: string): string {
    if (value) {
      const dataFormatada = moment(value).format('DD/MM/YYYY');
      return dataFormatada;
    }
    return '';
  }

 /* dataServidor(value: string): string {
    if (value) {
      console.log(moment(value).toDate());
      const date: Date = moment(value).toDate();
      const ano: any =  date.getDay();
      const mes: any =  date.getMonth();
      const dia: any =  date.getFullYear();
      const dataFormatada = dia + '-' + mes + '-' + ano;
      console.log(dataFormatada);
      return dataFormatada;
    }
    return '';
  }*/
}
