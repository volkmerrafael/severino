package com.volkmer.godinho.severino.resource.importacao;

import java.util.Map;

import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.resource.anomes.AnoMesResource;

public class ProcessaAnoMes {

	public AnoMes processar(Map<String, AnoMes> mapaAnoMes, AnoMes anomes) throws Exception {
		
		AnoMes am = new AnoMes();
		
		am = mapaAnoMes.get(anomes.getAno()+""+anomes.getMes());
		
		if (am!=null) {
			return am;
		} else {
			try (AnoMesResource anomesRes = new AnoMesResource()) {
				
				AnoMes anomesPorAnoMes = anomesRes.buscaPorAnoMes(anomes.getAno(), anomes.getMes());
				if (anomesPorAnoMes!=null) {
					return anomesPorAnoMes;
				}
				anomesRes.incluir(anomes);
				mapaAnoMes.put(anomes.getAno()+""+anomes.getMes(), anomes);
				return anomes;
				
			}
		}

	}
	
}
