import { Acesso } from './acesso';
import { Departamento } from './departamento';
import { Funcao } from './funcao';
import { Empresa } from './empresa';

export class Usuario {
  id: number;
  nome: string;
  senha: string;
  acesso: Acesso;
  departamento: Departamento;
  funcao: Funcao;
  pis: string;
  cpf: string;
  rg: string;
  telefone: string;
  celular: string;
  email: string;
  data_admissao: string;
  usuario_jira: string;
  empresa: Empresa;
}
