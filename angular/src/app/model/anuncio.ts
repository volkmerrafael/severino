import { Loja } from "./loja";
import { Usuario } from "./usuario";
import { Estado } from "./estado";
import { Cidade } from "./cidade";
import { Categoria } from "./categoria";
import { SubCategoria } from "./sub-categoria";

export class Anuncio {

  id: number;
  titulo: string;
  descricao: string;
  dataHoraCadastro: Date;
  destaqueCapa: Boolean;
  valor: number;
  status: string;
  loja: Loja;
  usuario: Usuario;
  imagemPrincipal: string;
  visualizacoes: number;
  tituloUrl: string;
  estadoName: string;
  estadoUrl: string;
  cidadeName: string;
  cidadeUrl: string;
  categoriaName: string;
  categoriaUrl: string;
  subCategoriaName: string;
  subCategoriaUrl: string;
  url: string;
  cep: string;
  telefone: string;
  email: string;
  estado: Estado;
  cidade: Cidade;
  categoria: Categoria;
  subCategoria: SubCategoria;

}
