import { PrioridadeJira } from "./prioridadeJira";

export class PrioridadeInfo {
    total_registros: number;
    registros_atrasados: number;
    registros_no_prazo: number;
    lista_prioridades: PrioridadeJira[];
}
