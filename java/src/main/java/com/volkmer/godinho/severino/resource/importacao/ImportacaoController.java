package com.volkmer.godinho.severino.resource.importacao;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.Importacao;

@Path("/importacao")
public class ImportacaoController extends ControllerCRUD<Importacao, ImportacaoResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@Override
	public ImportacaoResource newResource() {
		return new ImportacaoResource();
	}
	
}
