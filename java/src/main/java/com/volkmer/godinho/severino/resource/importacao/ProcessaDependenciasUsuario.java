package com.volkmer.godinho.severino.resource.importacao;

import com.volkmer.godinho.severino.entity.Departamento;
import com.volkmer.godinho.severino.entity.Empresa;
import com.volkmer.godinho.severino.entity.Funcao;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.departamento.DepartamentoResource;
import com.volkmer.godinho.severino.resource.empresa.EmpresaResource;
import com.volkmer.godinho.severino.resource.funcao.FuncaoResource;

public class ProcessaDependenciasUsuario {

	public Usuario buscaOuCriaDependenciaUsuario(Usuario usuario) throws Exception {
		
		//Empresa verfica se encontra cadastrada no banco caso não encontre cria
		Empresa empresa = new Empresa();
		try (EmpresaResource empresaRes = new EmpresaResource()) {
			empresa = empresaRes.buscaPorRazaoSocial(usuario.getEmpresa().getRazao_social());
			
			if (empresa==null) {
				empresa = empresaRes.incluir(usuario.getEmpresa());
			}
			
			usuario.setEmpresa(empresa);
		}
		
		//Função verifica se encontra cadastrada banco caso não encontre cria
		Funcao funcao = new Funcao();
		try (FuncaoResource funcaoRes = new FuncaoResource()) {
			funcao = funcaoRes.buscaPorNome(usuario.getFuncao().getNome());
			
			if (funcao==null) {
				funcao = funcaoRes.incluir(usuario.getFuncao());
			}
			
			usuario.setFuncao(funcao);
		}
		
		//Departamento verifica se encontra cadastrada banco caso não encontre cria
		Departamento departamento = new Departamento();
		try (DepartamentoResource departamentoRes = new DepartamentoResource()) {
			departamento = departamentoRes.buscaPorNome(usuario.getDepartamento().getNome());
			
			if (departamento==null) {
				departamento = departamentoRes.incluir(usuario.getDepartamento());
			}
			
			usuario.setDepartamento(departamento);
		}
		
		return usuario;
	}
	
}
