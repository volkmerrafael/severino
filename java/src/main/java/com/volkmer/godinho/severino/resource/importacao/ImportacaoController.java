package com.volkmer.godinho.severino.resource.importacao;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.severino.entity.Importacao;
import com.volkmer.godinho.severino.entity.Usuario;

import io.swagger.annotations.Api;

@Api("Importação")
@Path("/importacao")
public class ImportacaoController {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;

	@HeaderParam("user-agent")
	String userAgent;
	
	@SuppressWarnings("resource")
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Importacao> listarImportacao() throws Exception {
		return new ImportacaoResource().listarImportacoes(userToken);
	}
	
	@SuppressWarnings("resource")
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Importacao gravar(Importacao importacao) throws Exception {
		return new ImportacaoResource().gravar(userToken, importacao);
	}

	@SuppressWarnings("resource")
	@GET
	@Path("/detalhar/usuarios/{importacao}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> lsitarUsuariosPorStatus(@PathParam("importacao") Integer importacao, @PathParam("status") Integer status) throws Exception {
		return new ImportacaoResource().listarUsuarios(userToken, importacao, status);
	}
	
}
