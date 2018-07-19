package com.volkmer.godinho.severino.resource.mod_controleponto.ponto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_controleponto.Ponto;
import com.volkmer.godinho.severino.entity.mod_geral.Configuracao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.entity.mod_jira.Issue;
import com.volkmer.godinho.severino.resource.mod_acesso.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.mod_geral.configuracao.ConfiguracaoResource;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;
import com.volkmer.godinho.severino.resource.mod_jira.issue.IssueResource;
import com.volkmer.godinho.severino.resource.mod_jira.worklog.Worklog;
import com.volkmer.godinho.severino.resource.mod_jira.worklog.WorklogResource;

public class PontoResource extends ResourceCRUD<Ponto> {
	
	public PontoResource() {
	}
	
	@Override
	public Class<Ponto> getModelClass() {
		return Ponto.class;
	}
	
	@Override
	protected void buscaPos(Ponto model) throws Exception {
		
		model.getImportacao().setArquivoimportacao(null);
		
	}
	
	private RestException erroUsuarioNaoPertenceAoCoordenadorLogado = new RestException("Usuário Não pertence ao coordenador logado.");
	//private RestException naoEncontradasInformacoesDePontoNoPeriodo = new RestException("Não foram encontradas informações de ponto no período informado.");
	
	@Override
	protected void alterarPre(Ponto model) throws Exception {

		if (model.getImportacao()==null) {
			Ponto pontoBanco = this.busca(model.getId());
			model.setImportacao(pontoBanco.getImportacao());	
		}
		
		//Caso tenha issues Processa as mesmas
		if (model.getIssues()!=null && model.getIssues().size()>0) {
			//Processa Issues
			this.processaIssuesPonto(model);
		} else {
			//Exclui issues do vinculadas ao ponto em alteração
			try (IssueResource issueRes = new IssueResource()) {						
				List<Issue> listaIssuesBanco = issueRes.buscaIssuesPorPonto(model);
				for (Issue issue : listaIssuesBanco) {
					issueRes.remover(issue);
				}
			} catch (NoResultException e) {
			}		
		}

	}

	public List<Ponto> listarPontos(Long usuarioid, String token, Integer ano, Integer mes, PontoStatus status) throws Exception {
	
		Usuario usuario = new Usuario();
		Usuario usuariodoid =  new Usuario();
		try (UsuarioResource usuRes = new UsuarioResource()) {
			
			usuario = usuRes.buscaUsuarioPeloToken(token);
			usuariodoid = usuRes.buscaUsuarioPeloId(usuarioid);

			//Usuário encontrado agora retorna a lista de ponto do usuário
			TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano order by p.data asc", Ponto.class);
			
			if (usuario.getAcesso().getTipo().equals(AcessoTipo.NORMAL)) {
				queryPonto.setParameter("usuario", usuario);
			} else if (usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
				if (usuariodoid.getDepartamento().getId().equals(usuario.getDepartamento().getId())) {
					queryPonto.setParameter("usuario", usuariodoid);
				} else {
					throw erroUsuarioNaoPertenceAoCoordenadorLogado;
				}
			}
			queryPonto.setParameter("mes", mes);
			queryPonto.setParameter("ano", ano);
			if (status!=null) {
				queryPonto.setParameter("status", status);
			}
			List<Ponto> lista = queryPonto.getResultList();
			
			if (lista==null || lista.size()==0) {
				//throw naoEncontradasInformacoesDePontoNoPeriodo;
			}
			
			Configuracao configuracao = new Configuracao();
			
			try (ConfiguracaoResource conRes = new ConfiguracaoResource()) {
				configuracao = conRes.busca(1);
			} catch (NoResultException e) {
			}
			
			for (Ponto ponto : lista) {
				
				//ponto.getImportacao().setArquivoimportacao(null);
				
				if (configuracao!=null && configuracao.getIntegra_jira()) {
					
					BigDecimal somaWorklogDiario = BigDecimal.ZERO;
					
					List<Worklog> listaWorklog = new ArrayList<>();
					
					try (WorklogResource worklogRes = new WorklogResource()) {
						listaWorklog = worklogRes.listarWorklogJira(ponto.getUsuario().getId(), ponto.getData().toString());
					} catch (NoResultException e) {
					}
					
					List<Issue> listaIssueBanco = new ArrayList<>();
					
					try (IssueResource workRes = new IssueResource()) {
						listaIssueBanco = workRes.buscaIssuesPorPonto(ponto);							
					}
					
					List<Issue> listaRetorno = new ArrayList<>();						
					
					if (listaIssueBanco!=null &&listaIssueBanco.size()>0) {
						
						for (Issue issue : listaIssueBanco) {
							issue.setPonto(null);
						}	
						
						listaRetorno.addAll(listaIssueBanco);							
						
						for (Worklog worklog : listaWorklog) {
							
							somaWorklogDiario = somaWorklogDiario.add(worklog.getTimeworked());
							
							Boolean achou = false;
							for (Issue issue : listaIssueBanco) {
								if (issue.getIssue().equals(worklog.getIssue().getIssue())) {																													
									achou = true;
									break;										
								}
							}
							if (!achou) {
								listaRetorno.add(worklog.getIssue());
							}
						}
						
						if (listaRetorno!=null && listaRetorno.size()>0) {
							ponto.setIssues(new ArrayList<Issue>()); 
							ponto.getIssues().addAll(listaRetorno);
						}
						
						
					} else {
						if (listaWorklog!=null && listaWorklog.size()>0) {
							
							for (Worklog worklog : listaWorklog) {
								
								somaWorklogDiario = somaWorklogDiario.add(worklog.getTimeworked());
								
								Issue issue = new Issue();
								issue = worklog.getIssue();
								if (listaRetorno==null || listaRetorno.size()==0) {
									listaRetorno.add(issue);
								} else {
									Boolean achou = false;
									for (Issue issueRetorno : listaRetorno) {
										if (issue.getIssue().equals(issueRetorno.getIssue())) {
											achou = true;
											break;
										}
									}
									
									if (!achou) {
										listaRetorno.add(issue);
									}
								}
		
							}
							
							if (listaRetorno!=null && listaRetorno.size()>0) {
								ponto.setIssues(new ArrayList<Issue>());
								ponto.getIssues().addAll(listaRetorno);									
							}

						}
					}
					
					ponto.setWorklog_diario(somaWorklogDiario);
					
				}
				
			}
			
			return lista;

		} catch (NoResultException e) {			
		}
		
		return null;
		
	}
	
	public Ponto buscaPorUsuarioData(Usuario usuario, LocalDate data) throws Exception {
		
		TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.usuario = :usuario and p.data = :data", Ponto.class);
		queryPonto.setParameter("usuario", usuario);
		queryPonto.setParameter("data", data);	
		
		Ponto ponto = new Ponto();
		
		try {
			ponto = queryPonto.getSingleResult();
			ponto.getImportacao().setArquivoimportacao(null);
		} catch (NoResultException e) {
			ponto = null;
		}
		
		return ponto;
		
	}

	public List<Ponto> buscaPorPeriodo(LocalDate inicio_periodo, LocalDate final_periodo) {
		
		TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.data between :inicio_periodo and :final_periodo ", Ponto.class);
		queryPonto.setParameter("final_periodo", final_periodo);
		queryPonto.setParameter("inicio_periodo", inicio_periodo);
		
		List<Ponto> lista = queryPonto.getResultList();
		
		lista.forEach(p -> p.getImportacao().setArquivoimportacao(null));
		
		return lista;
		
	}
	
	private void processaIssuesPonto(Ponto model) throws Exception {
		
		try (IssueResource issueRes = new IssueResource()) {
			
			List<Issue> listaIssuesBanco = issueRes.buscaIssuesPorPonto(model);
			
			//model.setIssues(new ArrayList<>());
			
			//Se não tem nada no banco tudo que vier será gravado
			if (listaIssuesBanco==null || listaIssuesBanco.size()==0) {
				for (Issue issue : model.getIssues()) {	
					Ponto pontoBanco = this.busca(model.getId());
					issue.setPonto(pontoBanco);
					issueRes.incluir(issue);
				}
			} else {
				
				List<Issue> listaManter = new ArrayList<>();
				
				//Valida o que deve ser deletado
				for (Issue issueBanco : listaIssuesBanco) {
					Boolean encontrou = false;
					for (Issue issue : model.getIssues()) {
						if (issueBanco.getIssue().equals(issue.getIssue())) {
							encontrou = true;
							break;
						}
					}
					if (!encontrou) {
						model.getIssues().remove(issueBanco);
						issueRes.remover(issueBanco);
					} else {
						listaManter.add(issueBanco);
					}
				}
				
				//Verifica o que deve ser incluido
				for (Issue issue : model.getIssues()) {
					Boolean encontrou = false;
					for (Issue issueBanco : listaIssuesBanco) {
						if (issue.getIssue().equals(issueBanco.getIssue())) {
							encontrou = true;
							break;
						}
					}
					if (!encontrou) {
						
						issue.setPonto(model);
						issueRes.incluir(issue);
						listaManter.add(issue);
		
					}
				}		
				model.setIssues(new ArrayList<>());
				model.getIssues().addAll(listaManter);
				
			}
			
			for (Issue issue : model.getIssues()) {						
				
				if (issue.getId()==null) {		
					issue.setPonto(model);
					issueRes.incluir(issue);								
				}							
				
			}
									
		} catch (NoResultException e) {
		}
	
	}

}
