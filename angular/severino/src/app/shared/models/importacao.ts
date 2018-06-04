import { ArquivoImportacao } from "./arquivoimportacao";

export class Importacao {
      id: number;
      nome: string;
      extensao: string;
      tamanho: number;
      inicio_periodo: string;
      final_periodo: string;
      data_hora_importacao: string;
      quantidade_usuario: number;
      usuario_com_debito_banco: number;
      usuario_com_credito_banco: number;
      usuario_com_marcacao_incorreta: number;
      usuario_sem_pendencias: number;
      tempo_importacao: string;
      status: string;
      arquivoimportacao: ArquivoImportacao;
}
