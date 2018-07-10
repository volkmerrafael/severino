package com.volkmer.godinho.severino.resource.mod_geral.usuario;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.ControllerCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Usuário")
@Path("/usuario")
public class UsuarioController extends ControllerCRUD<Usuario, UsuarioResource> {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;
	
	@Override
	public UsuarioResource newResource() {
		return new UsuarioResource();
	}
	
	@GET
	@Path("/listar")
	@ApiOperation(value = "Listar Usuário do Coordenador")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> listar() throws Exception {
		try (UsuarioResource pres = new UsuarioResource()) {
			return pres.listarUsuariosDoCoordenador(userToken);	
		} catch (Exception e) {
			throw e;
		}
	}
}
