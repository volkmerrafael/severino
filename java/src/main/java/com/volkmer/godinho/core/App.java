package com.volkmer.godinho.core;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Legenda;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.legenda.LegendaResource;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

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
					acesso.setNomeacesso("admin");
					acesso.setSenha("admin");
					acesso.setTipo(AcessoTipo.ADMIN);
					
					admin = new Usuario();
					admin.setNome("Administrador");
					admin.setEmail("admin@casa.com");
					admin.setAcesso(acesso);
					
					usuRes.incluir(admin);
					
				}
	
				/*//Coordenador
				Acesso acesso = new Acesso();
				acesso.setNomeacesso("fabiano.carniel");
				acesso.setSenha("fabiano.carniel");
				acesso.setTipo(AcessoTipo.COORDENADOR);
				
				Departamento departamento = new Departamento();
				departamento.setId((long) 4);
				departamento.setNome("Desenvolvimento Educação/Social");
				
				Funcao funcao = new Funcao();
				funcao.setId((long) 20);
				funcao.setNome("Coordenador");
				
				Usuario coordenandor = new Usuario();
				coordenandor.setNome("FABIANO CARNIEL");
				coordenandor.setEmail("fcarniel@ids.inf.br");
				coordenandor.setAcesso(acesso);
				coordenandor.setDepartamento(departamento);
				coordenandor.setFuncao(funcao);
				coordenandor.setData_admissao(LocalDate.now());
				coordenandor.setPis((long) 123456); 
				usuRes.incluir(coordenandor);
				*/
				
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