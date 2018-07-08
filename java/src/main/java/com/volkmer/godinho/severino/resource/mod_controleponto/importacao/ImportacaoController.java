package com.volkmer.godinho.severino.resource.mod_controleponto.importacao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_controleponto.Importacao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoStatus;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

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
	
	private RestException erroAoRealizarImportacao = new RestException("Erro ao realizar Importação.");
	private RestException naoForamEncontradasImportacoes = new RestException("Não Foram encontradas Importações");
	private RestException operacaoSoPodeSerRealizadaPorUsuarioAdministrador = new RestException("Operação só pode ser realizada por usuário administrador.");
	
	@GET
	@Path("/")
	@ApiOperation(value = "Listar Arquivos Importados")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Importacao> listarImportacao() throws Exception {
		try (ImportacaoResource imp = new ImportacaoResource()) {
			try (UsuarioResource usuRes = new UsuarioResource()) {
				if (usuRes.ehUsarioAdmin(userToken)) {
						return imp.listarImportacoes(userToken);
				}
			}
			throw operacaoSoPodeSerRealizadaPorUsuarioAdministrador;
		} catch (NoResultException e) {
			throw naoForamEncontradasImportacoes;
		}
	}
	
	@POST
	@Path("/")
	@ApiOperation(value = "Importar Arquivo")
	@ApiResponses(
	  @ApiResponse(
	    code=200,
	    message="Importado com Sucesso",
	    response = Importacao.class))
	@Produces(MediaType.APPLICATION_JSON)
	public Importacao importar(Importacao importacao) throws Exception {
		try (ImportacaoResource imp = new ImportacaoResource()) {
			try (UsuarioResource usuRes = new UsuarioResource()) {
				if (usuRes.ehUsarioAdmin(userToken)) {
					return imp.importar(userToken, importacao);
				}
			}
			throw operacaoSoPodeSerRealizadaPorUsuarioAdministrador;
		} catch (Exception e) {
			e.printStackTrace();
			throw erroAoRealizarImportacao;
		}
	}

	@GET
	@Path("/detalhar/usuarios/{importacao}/{status}")
	@ApiOperation(value = "Listar Usuários Importados por Importação e Status")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> lsitarUsuariosPorStatus(@PathParam("importacao") Long importacao, @PathParam("status") PontoStatus status) throws Exception {
		try (ImportacaoResource imp = new ImportacaoResource()) {
			try (UsuarioResource usuRes = new UsuarioResource()) {
				if (usuRes.ehUsarioAdmin(userToken)) {
					return imp.listarUsuarios(userToken, importacao, status);
				}
			}
			throw operacaoSoPodeSerRealizadaPorUsuarioAdministrador;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
