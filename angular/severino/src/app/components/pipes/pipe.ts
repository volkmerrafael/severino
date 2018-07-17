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
}
