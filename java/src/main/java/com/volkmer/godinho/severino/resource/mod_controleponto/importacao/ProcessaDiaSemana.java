package com.volkmer.godinho.severino.resource.mod_controleponto.importacao;

import java.util.Map;

import com.volkmer.godinho.severino.entity.mod_controleponto.DiaSemana;
import com.volkmer.godinho.severino.resource.mod_controleponto.diasemana.DiaSemanaResource;

public class ProcessaDiaSemana {

	public DiaSemana processar(Map<String, DiaSemana> mapaDiaSemana, DiaSemana diasemana) throws Exception {
		
		DiaSemana diasem = new DiaSemana();
		
		diasem = mapaDiaSemana.get(diasemana.getNome());
		if (diasem!=null) {
			return diasem;
		} else {
	
			try (DiaSemanaResource diasemanaRes = new DiaSemanaResource()) {
				
				DiaSemana diasemanaPorNome = diasemanaRes.buscaPorNome(diasemana.getNome());
				if (diasemanaPorNome!=null) {
					return diasemanaPorNome;
				}
				diasemanaRes.incluir(diasemana);
				mapaDiaSemana.put(diasemana.getNome(), diasemana);
				return diasemana;
				
			}
			
		}
	}
	
}
