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
import com.volkmer.godinho.severino.resource.ponto.PontoStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Importação")
@Path("/importacao")
public class ImportacaoController {

	@HeaderParam("user-token")
	String userToken;
	
	@HeaderParam("session-token")
	String sessionToken;
	
	@SuppressWarnings("resource")
	@GET
	@Path("/")
	@ApiOperation(value = "Listar Arquivos Importados")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Importacao> listarImportacao() throws Exception {
		return new ImportacaoResource().listarImportacoes(userToken);
	}
	
	@SuppressWarnings("resource")
	@POST
	@Path("/")
	@ApiOperation(value = "Importar Arquivo")
	@ApiResponses(
	  @ApiResponse(
	    code=200,
	    message="Importado com Sucesso",
	    response = Importacao.class))
	@Produces(MediaType.APPLICATION_JSON)
	public Importacao gravar(Importacao importacao) throws Exception {
		return new ImportacaoResource().gravar(userToken, importacao);
	}

	@SuppressWarnings("resource")
	@GET
	@Path("/detalhar/usuarios/{importacao}/{status}")
	@ApiOperation(value = "Listar Usuários Importados por Importação e Status")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> lsitarUsuariosPorStatus(@PathParam("importacao") Long importacao, @PathParam("status") PontoStatus status) throws Exception {
		return new ImportacaoResource().listarUsuarios(userToken, importacao, status);
	}
	
}
