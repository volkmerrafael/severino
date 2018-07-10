package com.volkmer.godinho.severino.resource.mod_estoque.produto;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_estoque.Produto;

import io.swagger.annotations.Api;


@Api("Produto")
@Path("/produto")
public class ProdutoController extends ControllerCRUD<Produto, ProdutoResource> {
	
	@HeaderParam("user-token")
	String userToken;
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public ProdutoResource newResource() {
		return new ProdutoResource();
	}

}
