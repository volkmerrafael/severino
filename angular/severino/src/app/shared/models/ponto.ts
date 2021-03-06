import { Usuario } from "./usuario";
import { Legenda } from "./legenda";
import { Justificativa } from './justificativa';
import { Issue } from "./issue";
import { Issues } from "./issues";

export class Ponto {

  id: number;
  data: any;
  diasemana: string;
  jornada: string;
  entrada1: string;
  entrada2: string;
  entrada3: string;
  entrada4: string;
  saida1: string;
  saida2: string;
  saida3: string;
  saida4: string;
  observacao: string;
  usuario: Usuario;
  legenda: Legenda;
  status: string;
  justificativa: string;
  issues: Issues[];
  minutos_trabalhados: number;
  minutos_debito: number;
  minutos_credito: number;
  worklog_diario: number;
}
