package com.volkmer.godinho.severino.resource.departamento;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Departamento;

public class DepartamentoResource extends ResourceCRUD<Departamento> {
	
	public DepartamentoResource() {
	}
	
	@Override
	public Class<Departamento> getModelClass() {
		return Departamento.class;
	}
	
}