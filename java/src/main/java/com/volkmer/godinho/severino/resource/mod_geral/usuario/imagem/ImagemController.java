package com.volkmer.godinho.severino.resource.mod_geral.usuario.imagem;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.imagem.Imagem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Imagem Usuário")
@Path("/imagem")
public class ImagemController extends ControllerCRUD<Imagem, ImagemResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@Override
	public ImagemResource newResource() {
		return new ImagemResource();
	}
	
	@GET
	@Path("/{usuario}")
	@ApiOperation(value = "Foto do Usuário")
	@Produces(MediaType.APPLICATION_JSON)
	public Imagem buscaImagemUsuario(@PathParam("usuario") Long usuarioid) throws Exception {
		try (ImagemResource ima = new ImagemResource()) {
			return ima.buscaImagem(usuarioid);
		} catch (NoResultException e) {
		}
		
		return null;
	}
	
}
