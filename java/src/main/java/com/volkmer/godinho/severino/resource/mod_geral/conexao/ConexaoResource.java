package com.volkmer.godinho.severino.resource.mod_geral.conexao;

import java.util.List;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_geral.Conexao;

public class ConexaoResource extends ResourceCRUD<Conexao> {
	
	public ConexaoResource() {
	}
	
	@Override
	public Class<Conexao> getModelClass() {
		return Conexao.class;
	}
	
	public Conexao buscaConexao(ConexaoSistema consistema) throws Exception { 
	
		Conexao conexao = new Conexao();
		
		List<Conexao> listaConexao = this.buscaTotos();
		
		for (Conexao con : listaConexao) {
			if (con.getConexao_sistema().equals(consistema)) {
				conexao = con;
				break;
			}
		}
		
		return conexao;
		
	}
}