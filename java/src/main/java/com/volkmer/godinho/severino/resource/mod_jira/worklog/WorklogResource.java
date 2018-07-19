package com.volkmer.godinho.severino.resource.mod_jira.worklog;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.core.util.validacao.ValidaData;
import com.volkmer.godinho.severino.entity.mod_geral.Conexao;
import com.volkmer.godinho.severino.entity.mod_geral.Configuracao;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.entity.mod_jira.Issue;
import com.volkmer.godinho.severino.resource.mod_geral.conexao.ConexaoResource;
import com.volkmer.godinho.severino.resource.mod_geral.conexao.ConexaoSistema;
import com.volkmer.godinho.severino.resource.mod_geral.configuracao.ConfiguracaoResource;
import com.volkmer.godinho.severino.resource.mod_geral.usuario.UsuarioResource;

public class WorklogResource extends ResourceCRUD<Worklog> {
	
	public WorklogResource() {
	}
	
	@Override
	public Class<Worklog> getModelClass() {
		return Worklog.class;
	}
	
	private RestException dataInvalida = new RestException("Data Inválida.");
	private RestException semIntegracaoJira = new RestException("Sistema não configurado para integração com Jira.");
	private RestException semConexaoJira = new RestException("É necessário o cadastramento da conexão com o banco de dados do Jira.");
	
	public List<Worklog> listarWorklogJira(Long usuarioid, String data) throws Exception {
		
		if (!ValidaData.validaDataFormatada(data)) {
			throw dataInvalida;
		}
		
		try (ConfiguracaoResource configRes = new ConfiguracaoResource()) {
			Configuracao configuracao = configRes.busca(1);
			if (configuracao==null || !configuracao.getIntegra_jira()) {
				throw semIntegracaoJira;
			}
		}
		
		try (ConexaoResource conRes = new ConexaoResource()) {
			
			Usuario usuario = new Usuario();
			
			try (UsuarioResource usuRes = new UsuarioResource()) {
				usuario = usuRes.buscaUsuarioPeloId(usuarioid);
				
				Conexao conexao = conRes.buscaConexao(ConexaoSistema.JIRA);
				
				if (conexao==null) {
					throw semConexaoJira;
				}
				
				String url = "jdbc:postgresql://"+conexao.getIp()+":"+conexao.getPorta()+"/"+conexao.getBanco();
				Connection connectionPostgres = DriverManager.getConnection(url, conexao.getUsuario(), conexao.getSenha());
				
				List<Worklog> listaWorklogJira = new ArrayList<>();
				
				String sql = new WorklogSql().buscaSqlWorklog(data,usuario.getUsuario_jira());
				
				if (usuario.getUsuario_jira()!="") {
					PreparedStatement pstmt = connectionPostgres.prepareStatement(
							sql
					);
					
					{
						pstmt.executeQuery();
					}
					
					connectionPostgres.close();
		
					ResultSet rsTabelas = pstmt.getResultSet();
					
			        while (rsTabelas.next()) {
			        	
			        	Worklog worklogJira = new Worklog();
			        	Issue issue = new Issue();
			        	issue.setIssue(rsTabelas.getString(1));
			        	issue.setSummary(rsTabelas.getString(2));
			        	worklogJira.setIssue(issue);
			        	worklogJira.setStartdate(rsTabelas.getString(3));
			        	worklogJira.setTimeworked(new BigDecimal(rsTabelas.getString(4)));
			        	
			        	listaWorklogJira.add(worklogJira);
			        	
			        }
			        
					return listaWorklogJira;
				}

			} catch (NoResultException e) {
			}
			
		} catch (NoResultException e) {
		}
		
		return null;
		
	}
	
}