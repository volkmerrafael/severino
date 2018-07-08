package com.volkmer.godinho.severino.resource.mod_geral.configuracao;

import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.Configuracao;

import io.swagger.annotations.Api;

@Api("Configuração")
@Path("/configuracao")
public class ConfiguracaoController extends ControllerCRUD<Configuracao, ConfiguracaoResource> {

	@Override
	public ConfiguracaoResource newResource() {
		return new ConfiguracaoResource();
	}
	
}
