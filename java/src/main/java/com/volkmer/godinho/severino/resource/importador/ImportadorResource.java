package com.volkmer.godinho.severino.resource.importador;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.entity.Departamento;
import com.volkmer.godinho.severino.entity.Funcao;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.anomes.AnoMesResource;
import com.volkmer.godinho.severino.resource.departamento.DepartamentoResource;
import com.volkmer.godinho.severino.resource.funcao.FuncaoResource;
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
	public void gravarPonto(ObjetoPontoCompleto obj, String userToken) throws Exception {
	
		Departamento departamento = this.processaDepartamento(obj.getDepartamento());
		Funcao funcao = this.processaFuncao(obj.getFuncao());
		Usuario usuario = this.processaUsuario(obj, departamento, funcao);
						
		//Grava o ponto e vincula ao mesmo ao cadastro o usuário ao qual pertence o ponto
		
		TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.usuario = :usuario and p.data = :data", Ponto.class);
		queryPonto.setParameter("usuario", usuario);
		queryPonto.setParameter("data", obj.getPonto().getData());
		
		Ponto ponto = new Ponto();
		
		try {
			ponto = queryPonto.getSingleResult();
			obj.getPonto().setId(ponto.getId());
			obj.getPonto().setUsuario(ponto.getUsuario());
			obj.getPonto().setAnomes(ponto.getAnomes());
			ponto = obj.getPonto();
			this.alterar(ponto);
			this.commit();
			
		} catch (Exception e) {
			if (e.getMessage().indexOf("No entity found for query")==-1) {
				e.printStackTrace();
			} else  {
				ponto = null;
			}
		}
		
		if (ponto==null) {
			
			//Busca ano mes
			TypedQuery<AnoMes> queryAnoMes = this.getEm().createQuery("select u from AnoMes u where u.ano = :ano and u.mes = :mes", AnoMes.class);
			queryAnoMes.setParameter("ano", obj.getPonto().getData().getYear());
			queryAnoMes.setParameter("mes", Integer.valueOf(obj.getPonto().getData().getMonth().getValue()));
			
			AnoMes anomes = new AnoMes();
			
			try {
					anomes = queryAnoMes.getSingleResult();
			} catch (Exception e) {
				if (e.getMessage().indexOf("No entity found for query")==-1) {
					e.printStackTrace();
				} else  {
					
					anomes = null;
				}
			}
			
			AnoMesResource anomesRes = new AnoMesResource();
			
			if (anomes==null) {
				anomes = new AnoMes();
				anomes.setAno(obj.getPonto().getData().getYear());
				anomes.setMes(obj.getPonto().getData().getMonthValue());
				anomesRes.incluir(anomes);
				anomesRes.commit();
			}
				
			ponto = obj.getPonto();
			ponto.setUsuario(usuario);
			ponto.setAnomes(anomes);			
			this.incluir(ponto);
			this.commit();
		}
		
	}

	private Usuario processaUsuario(ObjetoPontoCompleto obj, Departamento departamento, Funcao funcao) throws Exception {
		
		//Busca usuário pelo P.I.S.
		TypedQuery<Usuario> queryUsuario = this.getEm().createQuery("select u from Usuario u where u.pis = :pis", Usuario.class);
		queryUsuario.setParameter("pis", obj.getPis());	
		
		@SuppressWarnings("resource")
		UsuarioResource usuRes = new UsuarioResource();
		
		Usuario usuario = new Usuario();
		
		try {
				
				usuario = queryUsuario.getSingleResult();
				
				/*TypedQuery<Acesso> queryAcesso = this.getEm().createQuery("select a from Acesso a where a.nomeacesso = :nomeacesso", Acesso.class);
				queryAcesso.setParameter("nomeacesso", obj.getPis());
				Acesso acesso = queryAcesso.getSingleResult();
				
				//Atualiza usuário
				usuario.setAcesso(acesso);
				usuario.setDepartamento(departamento);
				usuario.setFuncao(funcao);
				usuRes.alterar(usuario);
				usuRes.commit();
				*/
		} catch (Exception e) {
			if (e.getMessage().indexOf("No entity found for query")==-1) {
				e.printStackTrace();
			} else  {
				usuario = null;
			}
		}
		

		
		//Caso não encontre o usuário cria o mesmo e também cria um acesso setando o pis como nomeacesso e senha
		if (usuario==null) {
			
			Acesso acesso = new Acesso();
			acesso.setNomeacesso(obj.getPis());
			acesso.setSenha(obj.getPis());
			
			usuario = new Usuario();
			usuario.setNome(obj.getFuncionario());
			usuario.setData_admissao(obj.getData_admissao());
			usuario.setPis(obj.getPis());
			usuario.setDepartamento(departamento);
			usuario.setFuncao(funcao);
			usuario.setEmail("");
			usuario.setAcesso(acesso);
			
			usuRes.incluir(usuario);
			
			usuRes.commit();
			
		}

		return usuario;
	}

	private Funcao processaFuncao(String nomeFuncao) throws Exception {
		
		//Busca Funcao pelo Nome
		TypedQuery<Funcao> queryFuncao = this.getEm().createQuery("select f from Funcao f where f.nome = :nomeFuncao", Funcao.class);
		queryFuncao.setParameter("nomeFuncao", nomeFuncao);	
		
		Funcao funcao = new Funcao();
		
		try {
				funcao = queryFuncao.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().indexOf("No entity found for query")==-1) {
				e.printStackTrace();
			} else  {
				funcao = null;
			}
		}
		
		@SuppressWarnings("resource")
		FuncaoResource funRes = new FuncaoResource();
		
		//Caso não encontre a função
		if (funcao==null) {
			
			funcao = new Funcao();
			funcao.setNome(nomeFuncao);
			funRes.incluir(funcao);
			funRes.commit();
			
		}

		return funcao;
		
	}

	private Departamento processaDepartamento(String nomeDepartamento) throws Exception {
		//Busca Departamento pelo Nome
		TypedQuery<Departamento> queryDepartamento = this.getEm().createQuery("select d from Departamento d where d.nome = :nomeDepartamento", Departamento.class);
		queryDepartamento.setParameter("nomeDepartamento", nomeDepartamento);	
		
		Departamento departamento = new Departamento();
		
		try {
				departamento = queryDepartamento.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().indexOf("No entity found for query")==-1) {
				e.printStackTrace();
			} else  {
				departamento = null;
			}
		}
		
		@SuppressWarnings("resource")
		DepartamentoResource depRes = new DepartamentoResource();
		
		//Caso não encontre a Departamento
		if (departamento==null) {
			
			departamento = new Departamento();
			departamento.setNome(nomeDepartamento);
			depRes.incluir(departamento);
			depRes.commit();
			
		}

		return departamento;
	}
	
}