package com.volkmer.godinho.severino.resource.questao;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Questao;

public class QuestaoResource extends ResourceCRUD<Questao> {
	
	public QuestaoResource() {
	}
	
	public QuestaoResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<Questao> getModelClass() {
		return Questao.class;
	}
	
}
