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
	
		TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select u from Usuario u where u.nome = :nome", Usuario.class);
		queryUsuario.setParameter("nome", obj.getFuncionario());
		
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
		
		//@SuppressWarnings("resource")
		UsuarioResource usuRes = new UsuarioResource();
		
		if (usuario==null) {
			
			Acesso acesso = new Acesso();
			acesso.setNomeacesso(obj.getPis());
			acesso.setSenha(obj.getPis());
			
			usuario = new Usuario();
			usuario.setNome(obj.getFuncionario());
			usuario.setData_admissao(obj.getData_admissao());
			usuario.setPis(obj.getPis());
			usuario.setFuncao(obj.getFuncao());
			usuario.setEmail("--");
			usuario.setAcesso(acesso);
			
			usuRes.incluir(usuario);
			
			usuRes.commit();
			
		}
		
		//TypedQuery<Ponto> query = this.getEm().createQuery("select p from Ponto p where p.id = :id", this.getModelClass());
		//query.setParameter("id", Long.valueOf(9999)); //objetoPontoCompleto.getPonto().getData()
		
		Ponto pontoNovo = new Ponto();
		
		pontoNovo = obj.getPonto();
		pontoNovo.setUsuario(usuario);
		this.incluir(pontoNovo);
		this.commit();
		
	}
	
}