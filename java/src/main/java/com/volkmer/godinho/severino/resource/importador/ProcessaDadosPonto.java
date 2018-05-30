package com.volkmer.godinho.severino.resource.importador;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPontoCompleto;
import com.volkmer.godinho.severino.resource.importador.modelos.Tempo;
import com.volkmer.godinho.severino.resource.ponto.PontoStatus;

public class ProcessaDadosPonto {

	public void processar(ObjetoPontoCompleto objetoPontoCompleto) {
	
		String e1 = objetoPontoCompleto.getPonto().getEntrada1();
		String e2 = objetoPontoCompleto.getPonto().getEntrada2();
		String e3 = objetoPontoCompleto.getPonto().getEntrada3();
		String e4 = objetoPontoCompleto.getPonto().getEntrada4();
		String s1 = objetoPontoCompleto.getPonto().getSaida1();
		String s2 = objetoPontoCompleto.getPonto().getSaida2();
		String s3 = objetoPontoCompleto.getPonto().getSaida3();
		String s4 = objetoPontoCompleto.getPonto().getSaida4();
		
		if (!this.marcacoesEstaoCorretas(e1,s1,e2,s2,e3,s3,e4,s4)) {
			objetoPontoCompleto.getPonto().setStatus(PontoStatus.MARCACAO_INCORRETA);
			objetoPontoCompleto.getPonto().setObservacao("Marcações Incorretas");
		} else {
			this.calculaDebitoECredito(e1,s1,e2,s2,e3,s3,e4,s4, objetoPontoCompleto.getPonto());
		}
		
		if (objetoPontoCompleto.getPonto().getStatus()==null) {
			
			if (objetoPontoCompleto.getPonto().getDiasemana().getNome().equals("Sáb") || objetoPontoCompleto.getPonto().getDiasemana().getNome().equals("Dom")) {
				objetoPontoCompleto.getPonto().setStatus(PontoStatus.SEM_INFORMACAO);
			}
			
			if (objetoPontoCompleto.getPonto().getObservacao()!=null && !objetoPontoCompleto.getPonto().getObservacao().equals("")) {
				if (objetoPontoCompleto.getPonto().getObservacao().equals("Férias")) {
					objetoPontoCompleto.getPonto().setStatus(PontoStatus.FERIAS);
				} else 
				if (objetoPontoCompleto.getPonto().getObservacao().equals("Atestado medico")) {
					objetoPontoCompleto.getPonto().setStatus(PontoStatus.ATESTADO_MEDICO);
				} else 
				if (objetoPontoCompleto.getPonto().getObservacao().equals("Ponto Facultativo")) {
					objetoPontoCompleto.getPonto().setStatus(PontoStatus.PONTO_FACULTATIVO);
				} else 
				if (objetoPontoCompleto.getPonto().getObservacao().equals("Falta Justificada - (F)")) {
					objetoPontoCompleto.getPonto().setStatus(PontoStatus.FALTA_JUSTIFICADA);
				} else 
				if (objetoPontoCompleto.getPonto().getObservacao().equals("Não Admitido")) {
					objetoPontoCompleto.getPonto().setStatus(PontoStatus.NAO_ADMITIDO);
				} 
			}
			
			if (objetoPontoCompleto.getPonto().getStatus()==null) {
				objetoPontoCompleto.getPonto().setStatus(PontoStatus.CORRETO);	
			}
			
		}
	}

	private void calculaDebitoECredito(
			String e1, String s1, String e2, String s2, 
			String e3, String s3, String e4, String s4,
			Ponto obj) {
		
		Integer totalMinutosTrabalhados = 0;
		
		Integer jornada = 8*60;
		
		if (obj.getJornada()!=null && obj.getJornada().getJornada()!=null) {
			jornada = (obj.getJornada().getJornada().getHour()*60) + obj.getJornada().getJornada().getMinute();
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
		
		if (totalMinutosTrabalhados!=0) {
			
			//Quando é Feriado e Domingo duplica horas extra
			//Se dia da semana for sabado ou domingo deve ser hora extra
			
			if (obj.getDiasemana().getNome().equals("Sáb")) {
				credito = totalMinutosTrabalhados;
				obj.setMinutos_credito(credito);
				obj.setStatus(PontoStatus.CREDITO);
				obj.setObservacao(this.transformaHoraMinutoEmString("+",credito)+" => Crédito BH");				
			} else if (obj.getDiasemana().getNome().equals("Dom") || (obj.getLegenda()!=null && obj.getLegenda().getSigla()!=null && obj.getLegenda().getSigla().equals("F"))) {
				credito = totalMinutosTrabalhados;
				obj.setMinutos_credito(credito);
				obj.setStatus(PontoStatus.CREDITO);
				obj.setObservacao(this.transformaHoraMinutoEmString("+",credito*2)+" => Crédito BH Especial");
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

	private String transformaHoraMinutoEmString(String operador, Integer minutos) {
		String retorno = "";
		
		if (minutos/60!=0) {
			retorno += minutos/60+"h "+minutos%60+"m";
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
