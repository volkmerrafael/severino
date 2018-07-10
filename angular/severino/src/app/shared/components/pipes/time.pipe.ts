import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'formatarHora'
})

export class FormatarMinutosPipe implements PipeTransform {

    horas: number;
    hora: string;
    minutos: number;
    minuto: string;
    total: number;
    result: string;
    string: string;
    p1: number;
    p2: number;
    retorno: string;

    transform(value: number): string {
        if (value < 0) {
            value = value * -1;
        }
        if (value === undefined) {
            this.result = "00:00";
        } else if (value < 60) {
            this.minutos = value;
            if (this.minutos < 10) {
                this.result = "00:0" + this.minutos;
            } else {
                this.result = "00:" + this.minutos;
            }
            return this.result;
        } else if (value % 60 === 0) {
            this.horas = value / 60;
            if (this.horas < 10) {
                this.result = "0" + this.horas + ":00";
            } else {
                this.result = this.horas + ":00";
            }
            return this.result;
        } else {
            this.minutos = value % 60;
            this.total = value - this.minutos;
            this.horas = this.total / 60;
            if (this.minutos < 10) {
                this.minuto = ":0" + this.minutos;
            } else {
                this.minuto = ":" + this.minutos;
            }
            if (this.horas < 10) {
                this.hora = "0" + this.horas;
            } else {
                this.hora = "" + this.horas;
            }
            this.result = this.hora + this.minuto;
            return this.result;
        }
    }
    horaTransform(value: number) {
        if (value < 1) {
            this.minutos = Math.trunc(value * 60);
            if (this.minutos >= 10) {
            this.minuto = "00:" + this.minutos;
            } else {
            this.minuto = "00:0" + this.minutos;
            }
            this.result = this.minuto;
        } else if (value < 10) {
            this.horas = Math.trunc(value);
            this.minutos = (value - this.horas) * 60;
            if (this.minutos >= 10) {
                this.minuto = ":" + Math.trunc(this.minutos);
            } else {
                this.minuto = ":0" + Math.trunc(this.minutos);
            }
            this.result = "0" + this.horas + this.minuto;
        } else {
            this.horas = Math.trunc(value);
            this.minutos = (value - this.horas) * 60;
            if (this.minutos >= 10) {
                this.minuto = ":" + Math.trunc(this.minutos);
            } else {
                this.minuto = ":0" + Math.trunc(this.minutos);
            }
            this.result = this.horas + this.minuto;
        }
            return this.result;
    }

    buscar(text: string, posicao1: any, posicao2: any) {
        this.string = text;
        this.p1 = posicao1;
        this.p2 = posicao2;
        this.retorno = this.string.slice(this.p1, this.p2);
        return this.retorno;
    }
}
