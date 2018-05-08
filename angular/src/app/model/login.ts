import { Usuario } from "./usuario";

export interface Login {
  
    nomeacesso: String;
    senha: string;
    usertoken: string; 
    sessaotoken: string;         
    Usuario: Usuario;

}
