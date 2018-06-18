package com.volkmer.godinho.severino.resource.jira.prioridade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.Conexao;
import com.volkmer.godinho.severino.resource.conexao.ConexaoResource;
import com.volkmer.godinho.severino.resource.conexao.ConexaoSistema;

public class PrioridadeResource extends ResourceCRUD<Prioridade> {
	
	public PrioridadeResource() {
	}
	
	@Override
	public Class<Prioridade> getModelClass() {
		return Prioridade.class;
	}
	
	private RestException erroConexaoComJiraNaoCadastrada = new RestException("Conexão com o Jira não Cadastrada.");
	
	public PrioridadesInfo listarPrioridadeJira() throws Exception {
		
		try (ConexaoResource conRes = new ConexaoResource()) {
			
			Conexao conexao = conRes.buscaConexao(ConexaoSistema.JIRA);
			
			if (conexao==null) {
				throw erroConexaoComJiraNaoCadastrada;
			}
			
			String url = "jdbc:postgresql://"+conexao.getIp()+":"+conexao.getPorta()+"/"+conexao.getBanco();
			Connection connectionPostgres = DriverManager.getConnection(url, conexao.getUsuario(), conexao.getSenha());
			
			List<Prioridade> listaPrioridadeJira = new ArrayList<>();
			PrioridadesInfo prioridadeInfo = new PrioridadesInfo();
			
			String sql = new PrioridadesSql().buscaSqlPrioridades();
			
			PreparedStatement pstmt = connectionPostgres.prepareStatement(
					sql
			);
			
			{
				pstmt.executeQuery();
			}
			
			connectionPostgres.close();

			ResultSet rsTabelas = pstmt.getResultSet();
			
	        while (rsTabelas.next()) {
	        	
	        	Prioridade prioridadeJira = new Prioridade();
	        	prioridadeJira.setProject_name(rsTabelas.getString(1));
	        	prioridadeJira.setPkey(rsTabelas.getString(2));
	        	prioridadeJira.setIssueenum(rsTabelas.getString(3));
	        	prioridadeJira.setSummary(rsTabelas.getString(4));
	        	prioridadeJira.setCliente(rsTabelas.getString(5));
	        	prioridadeJira.setPendenteem(rsTabelas.getString(6));
	        	prioridadeJira.setT(rsTabelas.getString(7));
	        	prioridadeJira.setAno_restante(rsTabelas.getString(8));
	        	prioridadeJira.setTemp_atendimento(rsTabelas.getString(9));
	        	prioridadeJira.setIssueid(rsTabelas.getString(10));        	
	        	prioridadeJira.setProject(rsTabelas.getString(11));
	        	prioridadeJira.setSitema(rsTabelas.getString(12));
	        	prioridadeJira.setClassificacao(rsTabelas.getString(13));
	        	prioridadeJira.setInterna_externa(rsTabelas.getString(14));
	           	prioridadeJira.setTipo_classificacao(rsTabelas.getString(15));
	           	prioridadeJira.setAssignee(rsTabelas.getString(16));
	           	prioridadeJira.setCreator(rsTabelas.getString(17));
	           	prioridadeJira.setIssuetype(rsTabelas.getString(18));
	           	
	           	if (rsTabelas.getString(19)!="") {
	           		if (rsTabelas.getString(19).equals("1")) {
	           			prioridadeJira.setPriorityid("Urgente");
	           		} else if (rsTabelas.getString(19).equals("2")) {
	           			prioridadeJira.setPriorityid("Alta");
	           		} else if (rsTabelas.getString(19).equals("3")) {
	           			prioridadeJira.setPriorityid("Media");
	           		} else if (rsTabelas.getString(19).equals("4")) {
	           			prioridadeJira.setPriorityid("Baixa");
	           		} else  if (rsTabelas.getString(19).equals("5")) {
	           			prioridadeJira.setPriorityid("Indeterminada");
	           		} 
	           	}
	           		        
	           	prioridadeJira.setResolution(rsTabelas.getString(20));
	           	prioridadeJira.setIssuesstatus(rsTabelas.getString(21));
	           	prioridadeJira.setCreated(rsTabelas.getString(22));
	           	prioridadeJira.setUpdated(rsTabelas.getString(23));
	           	prioridadeJira.setDuedate(rsTabelas.getString(24));
	           	prioridadeJira.setResolutiondate(rsTabelas.getString(25));
	           	prioridadeJira.setTimespent(rsTabelas.getString(26));
	        	prioridadeJira.setAno_estouro(rsTabelas.getString(27));
	        	
	        	listaPrioridadeJira.add(prioridadeJira);
	        	
	        }
	        
	        Integer contadorAtrasos = 0;
	        for (Prioridade prioridade : listaPrioridadeJira) {
	        
	        	if (prioridade.getT().equals("-100.00") || prioridade.getT().equals("0.00")) {
	        		contadorAtrasos++;
	        	}
	        	
			}
	        
	        prioridadeInfo.setTotal_registros(listaPrioridadeJira.size()+1);
	        prioridadeInfo.setRegistros_atrasados(contadorAtrasos);
	        prioridadeInfo.setRegistros_no_prazo(prioridadeInfo.getTotal_registros()-prioridadeInfo.getRegistros_atrasados());
	        
	        prioridadeInfo.setLista_prioridades(listaPrioridadeJira);
	        
			return prioridadeInfo;
			
		} catch (NoResultException e) {
		}
		
		return null;
		
	}
	
	
}