package com.volkmer.godinho.severino.resource.funcao;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Funcao;

public class FuncaoResource extends ResourceCRUD<Funcao> {
	
	public FuncaoResource() {
	}
	
	@Override
	public Class<Funcao> getModelClass() {
		return Funcao.class;
	}
	
}