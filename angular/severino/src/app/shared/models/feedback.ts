import { Usuario } from "./usuario";

export class Feedback {

    id: number;
    descricao: string;
    usuario: Usuario;
    data_hora: string;
    tipo: string;
    status: string;
}
