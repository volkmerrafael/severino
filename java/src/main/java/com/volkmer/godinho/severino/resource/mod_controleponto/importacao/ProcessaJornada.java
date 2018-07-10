package com.volkmer.godinho.severino.resource.mod_controleponto.importacao;

import java.util.Map;

import com.volkmer.godinho.severino.entity.mod_controleponto.Jornada;
import com.volkmer.godinho.severino.resource.mod_controleponto.jornada.JornadaResource;

public class ProcessaJornada {

	public Jornada processar(Map<String, Jornada> mapaJornada, Jornada jornada) throws Exception {
		
		Jornada jor = new Jornada();
		
		jor = mapaJornada.get(jornada.getPeriodo_jornada());
		
		if (jor!=null) {
			return jor;
		} else {
			try (JornadaResource jornadaRes = new JornadaResource()) {
				
				Jornada jorndaPorPeriodoJornada = jornadaRes.buscaPorPeriodoJornada(jornada.getPeriodo_jornada());
				if (jorndaPorPeriodoJornada!=null) {
					return jorndaPorPeriodoJornada;
				}
				jornadaRes.incluir(jornada);
				mapaJornada.put(jornada.getPeriodo_jornada(), jornada);
				return jornada;
				
			}
		}
	}
	
}
