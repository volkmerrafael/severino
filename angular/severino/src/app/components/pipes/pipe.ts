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

  dataServidor(value: string): string {
    if (value) {
      const ano: String = '' + moment(value).year;
      const mes: String = '' + moment(value).month;
      const dia: String = '' + moment(value).day;
      const dataFormatada = ano + '-' + mes + '-' + dia;
      return dataFormatada;
    }
    return '';
  }
}
