import { Acesso } from './acesso';
import { Departamento } from './departamento';
import { Funcao } from './funcao';

export class Usuario {

  nome: string;
  nomeacesso: string;
  senha: string;
  acesso: Acesso;
  departamento: Departamento;
  funcao: Funcao;
  pis: string;
  email: string;
  data_admissao: string;
}
