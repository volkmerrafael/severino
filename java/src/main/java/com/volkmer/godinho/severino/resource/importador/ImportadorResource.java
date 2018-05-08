package com.volkmer.godinho.severino.resource.importador;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPontoCompleto;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

public class ImportadorResource extends ResourceCRUD<Ponto> {

	public ImportadorResource() {
	}
	
	@Override
	public Class<Ponto> getModelClass() {
		return Ponto.class;
	}
	
	@SuppressWarnings("resource")
	public void gravarPonto(ObjetoPontoCompleto obj) throws Exception {
	
		//Busca usuário pelo P.I.S.
		TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select u from Usuario u where u.pis = :pis", Usuario.class);
		queryUsuario.setParameter("pis", obj.getPis());
		
		Usuario usuario = new Usuario();
		
		try {
				usuario = queryUsuario.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().indexOf("No entity found for query")==-1) {
				e.printStackTrace();
			} else  {
				usuario = null;
			}
		}
		
		UsuarioResource usuRes = new UsuarioResource();
		
		//Caso não encontre o usuário cria o mesmo e também cria um acesso setando o pis como nomeacesso e senha
		if (usuario==null) {
			
			Acesso acesso = new Acesso();
			acesso.setNomeacesso(obj.getPis());
			acesso.setSenha(obj.getPis());
			
			usuario = new Usuario();
			usuario.setNome(obj.getFuncionario());
			usuario.setData_admissao(obj.getData_admissao());
			usuario.setPis(obj.getPis());
			usuario.setDepartamento(obj.getDepartamento());
			usuario.setFuncao(obj.getFuncao());
			usuario.setEmail("--");
			usuario.setAcesso(acesso);
			
			usuRes.incluir(usuario);
			
			usuRes.commit();
			
		}
		
		//Grava o ponto e vincula ao mesmo ao cadastro o usuário ao qual pertence o ponto
		Ponto pontoNovo = new Ponto();
		pontoNovo = obj.getPonto();
		pontoNovo.setUsuario(usuario);
		this.incluir(pontoNovo);
		this.commit();
		
	}
	
}