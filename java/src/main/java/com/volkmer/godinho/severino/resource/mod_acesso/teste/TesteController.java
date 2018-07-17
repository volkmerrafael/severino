package com.volkmer.godinho.severino.resource.mod_acesso.teste;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.core.dao.ListaDeClasses;
import com.volkmer.godinho.core.rest.RestSessao;
import com.volkmer.godinho.severino.entity.mod_acesso.Acesso;
import com.volkmer.godinho.severino.entity.mod_acesso.ObjetoTeste;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoResource;

import io.swagger.annotations.Api;

@Api("Teste")
@Path("/teste")
public class TesteController {

	@Inject
	RestSessao sessao; 
	
	@Context
	ServletContext context;
	
	@GET
	@Path("/ping")
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML}) 
	public String ping() {
		String msg = "ping: " + LocalDateTime.now();
		System.out.println(msg);
		return msg;
		
	}
	
	@GET
	@Path("/existeNomeAcesso/{nomeacesso}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean ping(@PathParam("nomeacesso") String nomeacesso) {
		
		@SuppressWarnings("resource")
		AcessoResource acessoRes = new AcessoResource();
				
		Acesso acesso = acessoRes.buscaPorNomeDeAcesso(nomeacesso);
		
		if (acesso!=null) {
			return true;
		} else {
			return false;
		}
		
	}
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public ObjetoTeste json() {
		ObjetoTeste obj = new ObjetoTeste();
		obj.criar();
		return obj;
	}
	
	@GET
	@Path("/verificaEmail/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean verificaEmail(@PathParam("email") String email) {
		if (email!=null) {
			if (email.equals("rafael.volkmer1@gmail.com")) {
				return true;
			}
		}
		return false;
	}
	
	@GET
	@Path("/xml")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ObjetoTeste xml() {
		ObjetoTeste obj = new ObjetoTeste();
		obj.criar();
		return obj;
	}
	
	@GET
	@Path("/testelistaclasses")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean testelistaclasses() {
		
		try {
			ListaDeClasses deClasses = new ListaDeClasses("com.volkmer.godinho.severino.entity");
			List<Class<?>> classes = deClasses.getClasses();
			
			boolean temObjetoTeste = false;
			boolean temUsuario = false;
			
			for (Class<?> classe : classes) {
				if (ObjetoTeste.class.equals(classe)) {
					temObjetoTeste = true;
				} else if (Usuario.class.equals(classe)) {
					temUsuario = true;
				}
			}
			
			if (temUsuario && temObjetoTeste) {
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
	
	@GET
	@Path("/data")
	@Produces(MediaType.APPLICATION_JSON)
	public LocalDate dataAtual() {
		return LocalDate.now();
	}
	
}
