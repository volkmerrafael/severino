package com.volkmer.godinho.severino.resource.mod_geral.configuracao;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.Configuracao;

public class ConfiguracaoResource extends ResourceCRUD<Configuracao> {
	
	public ConfiguracaoResource() {
	}
	
	@Override
	public Class<Configuracao> getModelClass() {
		return Configuracao.class;
	}
	
}