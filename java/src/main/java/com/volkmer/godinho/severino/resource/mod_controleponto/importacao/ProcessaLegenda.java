package com.volkmer.godinho.severino.resource.mod_controleponto.importacao;

import java.util.Map;

import com.volkmer.godinho.severino.entity.mod_controleponto.Legenda;
import com.volkmer.godinho.severino.resource.mod_controleponto.legenda.LegendaResource;

public class ProcessaLegenda {

	public Legenda processar(Map<String, Legenda> mapaLegenda, Legenda legenda) throws Exception {
		
		Legenda leg = new Legenda();
		
		leg = mapaLegenda.get(legenda.getSigla());
		
		if (leg!=null) {
			return leg;
		} else {
			try (LegendaResource legendaRes = new LegendaResource()) {
				
				Legenda legendaPorSigla = legendaRes.buscaPorSigla(legenda.getSigla());
				if (legendaPorSigla!=null) {
					return legendaPorSigla;
				}
				legendaRes.incluir(legenda);
				mapaLegenda.put(legenda.getSigla(), legenda);
				return legenda;
	
			}
		}
		
	}
	
}
