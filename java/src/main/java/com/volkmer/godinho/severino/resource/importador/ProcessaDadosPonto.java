package com.volkmer.godinho.severino.resource.importador;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.resource.importador.modelos.Tempo;
import com.volkmer.godinho.severino.resource.ponto.PontoStatus;

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
		
		if (!this.marcacoesEstaoCorretas(e1,s1,e2,s2,e3,s3,e4,s4)) {
			ponto.setStatus(PontoStatus.MARCACAO_INCORRETA);
			ponto.setObservacao("Marcações Incorretas");
		} else {
			this.calculaDebitoECreditoEAbonoETrabalhadas(e1,s1,e2,s2,e3,s3,e4,s4, ponto);
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

	private void calculaDebitoECreditoEAbonoETrabalhadas(
			String e1, String s1, String e2, String s2, 
			String e3, String s3, String e4, String s4,
			Ponto obj) {
		
		Integer totalMinutosTrabalhados = 0;
		
		Integer jornada = 8*60;
		
		if (obj.getJornada()!=null && obj.getJornada().getHoras()!=null) {
			jornada = (obj.getJornada().getHoras().getHour()*60) + obj.getJornada().getHoras().getMinute();
		}
		
		Integer totalMinutosObrigatorios = jornada;
		Integer tolerancia = 10;
		
		if (e1!=null && !e1.equals("") && s1!=null && !s1.equals("")) {
			Tempo tempoIni = this.converteStringParaHoraMinutoRetornaTempo(e1);
			Tempo tempoFin = this.converteStringParaHoraMinutoRetornaTempo(s1);
			totalMinutosTrabalhados += this.retornaPeriodoEntreHorariosEmMinutos(tempoIni.getHora(), tempoIni.getMinuto(), tempoFin.getHora(), tempoFin.getMinuto());
		}
		if (e2!=null && !e2.equals("") && s2!=null && !s2.equals("")) {
			Tempo tempoIni = this.converteStringParaHoraMinutoRetornaTempo(e2);
			Tempo tempoFin = this.converteStringParaHoraMinutoRetornaTempo(s2);
			totalMinutosTrabalhados += this.retornaPeriodoEntreHorariosEmMinutos(tempoIni.getHora(), tempoIni.getMinuto(), tempoFin.getHora(), tempoFin.getMinuto());
		}
		if (e3!=null && !e3.equals("") && s3!=null && !s3.equals("")) {
			Tempo tempoIni = this.converteStringParaHoraMinutoRetornaTempo(e3);
			Tempo tempoFin = this.converteStringParaHoraMinutoRetornaTempo(s3);
			totalMinutosTrabalhados += this.retornaPeriodoEntreHorariosEmMinutos(tempoIni.getHora(), tempoIni.getMinuto(), tempoFin.getHora(), tempoFin.getMinuto());
		}
		if (e4!=null && !e4.equals("") && s4!=null && !s4.equals("")) {
			Tempo tempoIni = this.converteStringParaHoraMinutoRetornaTempo(e4);
			Tempo tempoFin = this.converteStringParaHoraMinutoRetornaTempo(s4);
			totalMinutosTrabalhados += this.retornaPeriodoEntreHorariosEmMinutos(tempoIni.getHora(), tempoIni.getMinuto(), tempoFin.getHora(), tempoFin.getMinuto());
		}
		
		Integer debito = 0;
		Integer credito = 0;
		
		//Se tem joranada e não tem legenda quer dizer que deveria ter vindo trabalhar então será débito
		if (jornada!=null && totalMinutosTrabalhados==0 && obj.getLegenda()==null && obj.getObservacao()!=null && obj.getObservacao().indexOf("Débito")!=-1) {
			
			debito = jornada; //recebe jornada pois era o período em que o funcionário deveria estar trabalhando
			obj.setMinutos_debito(debito);
			obj.setStatus(PontoStatus.DEBITO);
			obj.setObservacao(this.transformaHoraMinutoEmString("-",debito)+" => Débito no BH");
				
		} else {
			
			//Se tem legenda separar horas trabalhadas de abono
			
			if (obj.getJornada()!=null && obj.getJornada().getHoras()!=null && obj.getLegenda()!=null && obj.getLegenda().getSigla()!="" && obj.getLegenda().getSigla().equals("A")) {
				
				obj.setMinutos_trabalhados(totalMinutosTrabalhados);
				
				if (totalMinutosTrabalhados<jornada) {
					//so conta abono
					//se não for compensação
					//e se não for férias
					if (obj.getObservacao()!=null && obj.getObservacao().indexOf("Compensação")==-1) {
						if (obj.getObservacao()!=null && obj.getObservacao().indexOf("Férias")==-1) {
							obj.setMinutos_abono(jornada-totalMinutosTrabalhados);
						}
					}
					
				}
				
			} else if (totalMinutosTrabalhados!=0) {
				
				//Horas trabalhadas considerando toleância
				
				if (totalMinutosTrabalhados>(totalMinutosObrigatorios+tolerancia) || totalMinutosTrabalhados<(totalMinutosObrigatorios-tolerancia)) {
					
					if (obj.getJornada()!=null && obj.getJornada().getHoras()!=null) {
						if (totalMinutosTrabalhados<jornada) {					
							obj.setMinutos_trabalhados(totalMinutosTrabalhados);
						} else {
							obj.setMinutos_trabalhados(jornada);
						}
					}
				} else {
					if (obj.getJornada()!=null && obj.getJornada().getHoras()!=null) {
						obj.setMinutos_trabalhados(jornada);
					}
				}	
				
				//Quando é Feriado e Domingo duplica horas extra
				//Se dia da semana for sabado ou domingo deve ser hora extra
				
				if (obj.getDiasemana().getNome().equals("Sáb")) {
					credito = totalMinutosTrabalhados;
					obj.setMinutos_credito(credito);
					obj.setStatus(PontoStatus.CREDITO);
					obj.setObservacao(this.transformaHoraMinutoEmString("+",credito)+" => Crédito BH");				
				} else if (obj.getDiasemana().getNome().equals("Dom") || (obj.getLegenda()!=null && obj.getLegenda().getSigla()!=null && obj.getLegenda().getSigla().equals("F"))) {
					credito = totalMinutosTrabalhados*2;
					//obj.setMinutos_trabalhados(obj.getMinutos_trabalhados()*2);
					obj.setMinutos_credito(credito);
					obj.setStatus(PontoStatus.CREDITO);
					obj.setObservacao(this.transformaHoraMinutoEmString("+",credito)+" => Crédito BH Especial");
				} else if (totalMinutosTrabalhados<(totalMinutosObrigatorios-tolerancia)) {
					//calcula débito somente se estorou a tolerancia
					debito = totalMinutosObrigatorios-totalMinutosTrabalhados;
					obj.setMinutos_debito(debito);
					obj.setStatus(PontoStatus.DEBITO);
					obj.setObservacao(this.transformaHoraMinutoEmString("-",debito)+" => Débito no BH");
				} else if (totalMinutosTrabalhados>(totalMinutosObrigatorios+tolerancia)){
					//calcula crédito somente se estourou tolerancia
					credito = totalMinutosTrabalhados-totalMinutosObrigatorios;
					obj.setMinutos_credito(credito);
					obj.setStatus(PontoStatus.CREDITO);
					obj.setObservacao(this.transformaHoraMinutoEmString("+",credito)+" => Crédito BH");
				}
				
			}
			
		}
		

				
	}

	private String transformaHoraMinutoEmString(String operador, Integer minutos) {
		String retorno = "";
		
		if (minutos/60!=0) {
			
			if (minutos%60==0) {
				retorno += minutos/60+"h";
			} else {
				retorno += minutos/60+"h "+minutos%60+"m";
			}
			
		} else {
			retorno += minutos%60+"m";
		}
		
		return retorno;
	}

	private Tempo converteStringParaHoraMinutoRetornaTempo(String valor) {
		
		Tempo tempo = new Tempo();
		
		tempo.setHora(Integer.valueOf(valor.substring(0,2)));
		tempo.setMinuto(Integer.valueOf(valor.substring(3,5)));
		
		return tempo;
		
	}
	
	private Integer retornaPeriodoEntreHorariosEmMinutos(Integer hora1, Integer minuto1, Integer hora2, Integer minuto2){
	    
		LocalDateTime oldDate = LocalDateTime.of(2016, Month.AUGUST, 1, hora1, minuto1, 0);
	    LocalDateTime newDate = LocalDateTime.of(2016, Month.AUGUST, 1, hora2, minuto2, 0);
	    
	    Duration duration = Duration.between(oldDate, newDate);
	    	    
	    return Integer.valueOf(String.valueOf(duration.getSeconds()/60));
	    
	}
	
	private Boolean marcacoesEstaoCorretas(
			String e1, String s1, String e2, String s2, 
			String e3, String s3, String e4, String s4) {
		
		Integer contador = 0;
		
		if (e1!=null && !e1.equals("")) {
			contador++;
		}
		if (s1!=null && !s1.equals("")) {
			contador++;
		}
		if (e2!=null && !e2.equals("")) {
			contador++;
		}
		if (s2!=null && !s2.equals("")) {
			contador++;
		}
		if (e3!=null && !e3.equals("")) {
			contador++;
		}
		if (s3!=null && !s3.equals("")) {
			contador++;
		}
		if (e4!=null && !e4.equals("")) {
			contador++;
		}
		if (s4!=null && !s4.equals("")) {
			contador++;
		}
		
		return this.ePar(contador);
		
	}	
	
	public boolean ePar(int numero)
	{
		if(numero % 2 == 0)
		   return true;
		else
		   return false;
	}
}
