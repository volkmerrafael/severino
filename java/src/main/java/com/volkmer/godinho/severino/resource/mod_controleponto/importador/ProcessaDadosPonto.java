package com.volkmer.godinho.severino.resource.mod_controleponto.importador;

import com.volkmer.godinho.severino.entity.mod_controleponto.Ponto;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoStatus;

public class ProcessaDadosPonto {

	public void processar(Ponto ponto) {
	
		String e1 = ponto.getEntrada1();
		String e2 = ponto.getEntrada2();
		String e3 = ponto.getEntrada3();
		String e4 = ponto.getEntrada4();
		String s1 = ponto.getSaida1();
		String s2 = ponto.getSaida2();
		String s3 = ponto.getSaida3();
		String s4 = ponto.getSaida4();
		
		if (!new ValidaMarcacoesIncorretas().ehCorreta(e1,s1,e2,s2,e3,s3,e4,s4)) {
			ponto.setStatus(PontoStatus.MARCACAO_INCORRETA);
			ponto.setObservacao("Marcações Incorretas");
		} else {
			new CalculaHorasDebitoECreditoEAbonoETrabalhadas().calcular(e1,s1,e2,s2,e3,s3,e4,s4, ponto);
		}
		
		if (ponto.getStatus()==null) {
			
			//Implementação referente a bug no sistema de ponto que gera descrições incorretas na observação
			if (ponto.getObservacao()!="" 
					&& (
					   ponto.getObservacao().indexOf("-----:-- [C] H. Pagas")!=-1
					|| ponto.getObservacao().indexOf("-----:-- [D] H. Pagas")!=-1   
					|| ponto.getObservacao().indexOf("00:00 [C] H. Pagas")!=-1 
					|| ponto.getObservacao().indexOf("00:00 [D] H. Pagas")!=-1 )
					) {
				
				if (ponto.getLegenda()!=null && ponto.getLegenda().getSigla()!="") {
					//Se legenda for A será setado Atestado médico na observação
					if (ponto.getLegenda().getSigla().equals("A")) {
						ponto.setObservacao("Atestado medico");
					} else {
						ponto.setObservacao("");
					}
				} else {
					ponto.setObservacao("");
				}
			}
			
			if (ponto.getDiasemana().getNome().equals("Sáb") || ponto.getDiasemana().getNome().equals("Dom")) {
				ponto.setStatus(PontoStatus.SEM_INFORMACAO);
			}
			
			if (ponto.getObservacao()!=null && !ponto.getObservacao().equals("")) {
				
				//Trantando esse retorno na ultima linha = 00:00 [D] H. Pagas
				
				if (ponto.getObservacao().indexOf("[D] H. Pagas")!=-1) {

				}
				
				if (ponto.getObservacao().equals("Férias")) {
					ponto.setStatus(PontoStatus.FERIAS);
				} else 
				if (ponto.getObservacao().equals("Atestado medico")) {
					ponto.setStatus(PontoStatus.ATESTADO_MEDICO);
				} else 
				if (ponto.getObservacao().equals("Certidão de Óbito")) {
					ponto.setStatus(PontoStatus.CERTIDAO_DE_OBITO);
				} else
				if (ponto.getObservacao().equals("Ponto Facultativo")) {
					ponto.setStatus(PontoStatus.PONTO_FACULTATIVO);
				} else 
				if (ponto.getObservacao().equals("Falta Justificada - (F)")) {
					ponto.setStatus(PontoStatus.FALTA_JUSTIFICADA);
				} else 
				if (ponto.getObservacao().equals("Não Admitido")) {
					ponto.setStatus(PontoStatus.NAO_ADMITIDO);
				} else 
				if (ponto.getObservacao().indexOf("Compensação do Feriado")!=-1) {
					ponto.setStatus(PontoStatus.COMPENSACAO);
				}
			}
			
			if (ponto.getLegenda()!=null && ponto.getLegenda().getSigla().equals("F")) {
				ponto.setStatus(PontoStatus.FERIADO);
				ponto.setObservacao("Feriado");
			}
			
			if (ponto.getStatus()==null) {
				ponto.setStatus(PontoStatus.CORRETO);	
			}
			
		}
	}
	
}
