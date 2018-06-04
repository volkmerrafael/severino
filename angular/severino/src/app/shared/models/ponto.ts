import { Usuario } from "./usuario";
import { Legenda } from "./legenda";

export class Ponto {

  id: number;
  data: string;
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
}
