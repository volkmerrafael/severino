import { Justificativa } from "./justificativa";

export class PontoEditado {
    id: number;
    diaSemana: string;
    data: string;
    jornadaId: number;
    entrada1: string;
    saida1: string;
    entrada2: string;
    saida2: string;
    entrada3: string;
    saida3: string;
    entrada4: string;
    saida4: string;
    observacao: string;
    justificativa: Justificativa;
    legendaId: number;
    status: string;
}
