package com.volkmer.godinho.core.timmer;
import java.time.Duration;
import java.time.LocalTime;

public class ControleTempoDeExecucao {

	private LocalTime tempo;
	
	public void inicio() {
		this.tempo = LocalTime.now();
	}

	public LocalTime fim() {

		LocalTime fim = LocalTime.now();
		
		//Calula Intervalo de tempo e retorna resultado
	    Duration dur = Duration.between(this.tempo, fim);
	    
	    LocalTime timefinal = LocalTime.of(
	    		Integer.valueOf((int)dur.getSeconds() / 3600),
	    		Integer.valueOf((int)(dur.getSeconds() % 3600) / 60),
	    		Integer.valueOf((int)dur.getSeconds() % 60)
	    		);
	      
		return timefinal;

	}

}
