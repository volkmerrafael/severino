import { Usuario } from "./usuario";
import { AnoMes } from "./anomes";
import { Importacao } from "./importacao";

export class ControleHoras {

  id: number;
  usuario: Usuario;
  anomes: AnoMes;
  importacao: Importacao;
  credito: number;
  debito: number;
  saldo: number;
  negativo: boolean;
  banco_de_horas: number;
  trabalhadas: number;
  abono: number;
}
