import { Usuario } from "./usuario";
import { AnoMes } from "./anomes";
import { Importacao } from "./importacao";

export class ControleHoras {

  id: number;
  usuario: Usuario;
  anomes: AnoMes;
  importacao: Importacao;
  horas_credito: string;
  horas_debito: string;
  banco_de_horas: string;
}
