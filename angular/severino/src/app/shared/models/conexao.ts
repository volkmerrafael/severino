import { Usuario } from "./usuario";

export class Conexao {

    ip: number;
    porta: number;
    banco: any;
    senha: string;
    data: string;
    conexao_sistema: string;
    usuario: Usuario;
}
