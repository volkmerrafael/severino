package com.volkmer.godinho.core;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import com.volkmer.godinho.severino.entity.mod_acesso.Acesso;
import com.volkmer.godinho.severino.entity.mod_controleponto.Legenda;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.mod_controleponto.legenda.LegendaResource;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

@ApplicationPath("/rest")
public class App extends Application {
	
	@Context
	public static ServletContext context;
	
	private static Integer teste = 0;
	
	@Override
	public Set<Object> getSingletons() {
		
		teste ++;
		if (teste==1) {
			//Em caso de base vazia cria usuário Admin
			try (UsuarioResource usuRes = new UsuarioResource()) {
								
				Usuario admin = usuRes.busca(1);
				if (admin==null) {
					
					Acesso acesso = new Acesso();
					acesso.setNomeacesso("administrador");
					acesso.setSenha("corinthians");
					acesso.setTipo(AcessoTipo.ADMIN);
					
					admin = new Usuario();
					admin.setNome("Administrador");
					admin.setEmail("admin@ids.inf.br");
					admin.setAcesso(acesso);
					
					usuRes.incluir(admin);
					
				} /*else {
				
					//setar senha do usuário administrador para default novamente
					admin.getAcesso().setNomeacesso("administrador");
					admin.getAcesso().setSenha("corinthians");
					admin.getAcesso().setTipo(AcessoTipo.ADMIN);

					usuRes.alterar(admin);
					
				}*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Em caso de base sem legendas cria legendas
			try (LegendaResource legRes = new LegendaResource()) {
				
				Legenda legenda = legRes.busca(1);
				if (legenda==null) {
					
					Legenda legendaAfastamento = new Legenda();
					legendaAfastamento.setNome("Afastamento");
					legendaAfastamento.setSigla("A");
					legRes.incluir(legendaAfastamento);

					Legenda legendaFeriado = new Legenda();
					legendaFeriado.setNome("Feriado");
					legendaFeriado.setSigla("F");
					legRes.incluir(legendaFeriado);
					
					Legenda legendaInclusaoBancoDeHoras = new Legenda();
					legendaInclusaoBancoDeHoras.setNome("Inclusão Banco de Horas");
					legendaInclusaoBancoDeHoras.setSigla("I");
					legRes.incluir(legendaInclusaoBancoDeHoras);
					
					Legenda legendaMarcacoesAlteradas = new Legenda();
					legendaMarcacoesAlteradas.setNome("Marcações Alteradas");
					legendaMarcacoesAlteradas.setSigla("*");
					legRes.incluir(legendaMarcacoesAlteradas);
					
					Legenda legendaJornadaAlternativa = new Legenda();
					legendaJornadaAlternativa.setNome("Jornada Alternativa");
					legendaJornadaAlternativa.setSigla("J");
					legRes.incluir(legendaJornadaAlternativa);
					
					Legenda legendaMudancaDeHorario = new Legenda();
					legendaMudancaDeHorario.setNome("Mudança de Horário");
					legendaMudancaDeHorario.setSigla("M");
					legRes.incluir(legendaMudancaDeHorario);
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		return super.getSingletons();
	}
	
	public App() {
	}
	
}