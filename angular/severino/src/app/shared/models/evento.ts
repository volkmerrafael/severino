import { TipoEvento } from "./tipoevento";
import { Usuario } from "./usuario";
import { PaticipanteEvento } from "./participanteEvento";

export class Funcao {
    id: number;
    descricao: string;
    usuario: Usuario;
    tipoevento: TipoEvento;
    data_hora_inicio: string;
    data_hora_termino: string;
    data_hora_limite_confirmacao: string;
    duracao: number;
    limite_convidados: number;
    limite_vagas: number;
    permite_convidados: string;
    participantes: PaticipanteEvento[];
    favorito: string;
}
