package com.volkmer.godinho.severino.resource.ponto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.Configuracao;
import com.volkmer.godinho.severino.entity.Justificativa;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.entity.Worklog;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.configuracao.ConfiguracaoResource;
import com.volkmer.godinho.severino.resource.jira.worklog.WorklogResource;
import com.volkmer.godinho.severino.resource.justificativa.JustificativaResource;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

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
	private RestException naoEncontradasInformacoesDePontoNoPeriodo = new RestException("Não foram encontradas informações de ponto no período informado.");
	
	@Override
	protected void alterarPre(Ponto model) throws Exception {
		
		Long id = model.getId();
			
		Ponto pontoBanco = new Ponto();
		
		if (id!=null) {
			
			if (model.getJustificativa()!=null) {
				
				pontoBanco = this.busca(id);
				
				//try (ArquivoImportacaoResource impRes = new ArquivoImportacaoResource()) {
				//	impRes.busca(pontoBanco.getImportacao().getArquivoimportacao().getId());
				//}
				
				Justificativa jus = new Justificativa();
			
			
				jus = model.getJustificativa();
				
				jus.setData_hora(LocalDateTime.now());
				
				if (model.getJustificativa().getId()==null) {
					try (JustificativaResource jusRes = new JustificativaResource()) {
						jusRes.incluir(jus);
					} 
				} else {
					try (JustificativaResource jusRes = new JustificativaResource()) {
						jusRes.alterar(jus);
					}
				}
				
				model.setJustificativa(jus);
				model.setImportacao(pontoBanco.getImportacao());
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
				throw naoEncontradasInformacoesDePontoNoPeriodo;
			}
			
			Configuracao configuracao = new Configuracao();
			
			try (ConfiguracaoResource conRes = new ConfiguracaoResource()) {
				configuracao = conRes.busca(1);
			} catch (NoResultException e) {
			}
			
			for (Ponto ponto : lista) {
				
				if (ponto.getStatus().equals(PontoStatus.CREDITO) || ponto.getStatus().equals(PontoStatus.DEBITO) || ponto.getStatus().equals(PontoStatus.MARCACAO_INCORRETA)) {
					ponto.getImportacao().setArquivoimportacao(null);
					
					List<Worklog> listaWorklog = new ArrayList<>();
					
					try (WorklogResource worRes = new WorklogResource()) {
						listaWorklog = worRes.listarWorklogJira(ponto.getUsuario().getId(), ponto.getData().toString());
					} catch (NoResultException e) {
					}
					
					if (configuracao!=null && configuracao.getIntegra_jira()) {
						if (ponto.getWorklogs()!=null && ponto.getWorklogs().size()>0) {
							for (Worklog worklogBanco : ponto.getWorklogs()) {							
								for (Worklog worklog : listaWorklog) {
									if (worklogBanco.getIssue().equals(worklog.getIssue())) {
										worklogBanco.setGravada(true);
									} else {
										worklog.setGravada(false);
										ponto.getWorklogs().add(worklog);
									}
								}
							}
						} else {
							ponto.setWorklogs(listaWorklog);
						}
					}
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

}
