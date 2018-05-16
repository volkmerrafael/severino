package com.volkmer.godinho.severino.resource.legenda;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Legenda;

public class LegendaResource extends ResourceCRUD<Legenda> {
	
	public LegendaResource() {
	}
	
	@Override
	public Class<Legenda> getModelClass() {
		return Legenda.class;
	}
	
}