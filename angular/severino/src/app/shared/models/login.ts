import { Usuario } from "./usuario";

export interface Login {
    nomeacesso: string;
    senha: string;
    usertoken: string;
    sessaotoken: string;
    usuario: Usuario;

}
