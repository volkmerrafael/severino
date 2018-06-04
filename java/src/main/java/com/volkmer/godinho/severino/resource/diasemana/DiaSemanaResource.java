package com.volkmer.godinho.severino.resource.diasemana;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.DiaSemana;

public class DiaSemanaResource extends ResourceCRUD<DiaSemana> {
	
	public DiaSemanaResource() {
	}
	
	@Override
	public Class<DiaSemana> getModelClass() {
		return DiaSemana.class;
	}
	
}