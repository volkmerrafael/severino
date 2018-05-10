package com.volkmer.godinho.severino.resource.anomes;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.AnoMes;

public class AnoMesResource extends ResourceCRUD<AnoMes> {
	
	public AnoMesResource() {
	}
	
	@Override
	public Class<AnoMes> getModelClass() {
		return AnoMes.class;
	}
	
}