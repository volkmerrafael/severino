package com.volkmer.godinho.severino.resource.configuracao;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Configuracao;

public class ConfiguracaoResource extends ResourceCRUD<Configuracao> {
	
	public ConfiguracaoResource() {
	}
	
	@Override
	public Class<Configuracao> getModelClass() {
		return Configuracao.class;
	}
	
}