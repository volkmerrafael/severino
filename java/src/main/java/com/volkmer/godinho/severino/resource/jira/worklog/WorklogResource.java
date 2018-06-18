package com.volkmer.godinho.severino.resource.jira.worklog;

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
import com.volkmer.godinho.core.util.ValidaData;
import com.volkmer.godinho.severino.entity.Conexao;
import com.volkmer.godinho.severino.entity.Configuracao;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.entity.Worklog;
import com.volkmer.godinho.severino.resource.conexao.ConexaoResource;
import com.volkmer.godinho.severino.resource.conexao.ConexaoSistema;
import com.volkmer.godinho.severino.resource.configuracao.ConfiguracaoResource;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

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
			        	
			        	worklogJira.setIssue(rsTabelas.getString(1));
			        	worklogJira.setSummary(rsTabelas.getString(2));
			        	worklogJira.setAuthor(rsTabelas.getString(3));
			        	worklogJira.setStartdate(rsTabelas.getString(4));
			        	worklogJira.setTimeworked(new BigDecimal(rsTabelas.getString(5)));
			        	
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