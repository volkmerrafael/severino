package com.volkmer.godinho.core.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.dao.EntityManagerUtil;
import com.volkmer.godinho.core.rest.Filtro;

public abstract class ResourceCRUD<Model> implements AutoCloseable {
	
	private EntityManager em = null;
	
	protected EntityManager getEm() {
		if (this.em==null) {
			this.em = EntityManagerUtil.getEntityManager();
		} else if (!this.em.isOpen()) {
			this.em = null;
			this.em = EntityManagerUtil.getEntityManager();			
		}
		return em;
	}

	private void setEm(EntityManager em) {
		this.em = em;
	}
	
	protected List<Filtro> getFiltros(ResourceCRUD<Model> res) {
		return null;
	}
	
	private String sql;
	
	public ResourceCRUD() {
	}
	
	public ResourceCRUD(ResourceCRUD<?> res) {
		this.setEm(res.getEm());
	}

	protected void buscaPos(Model model) throws Exception {}
	
	public List<Model> buscaTotos() throws Exception {
		
		sql = "from "+this.getModelClass().getSimpleName() + " m ";
		
		List<Filtro> filtros = this.getFiltros(this);
		if (filtros!=null && filtros.size()>0) {
			sql += " where ";
			
			filtros.forEach(filtro -> {
				sql += ("m."+filtro.getNome()+" = :"+filtro.getNome());
			});
		}
		
		TypedQuery<Model> query = this.getEm().createQuery(sql, this.getModelClass());
		
		if (filtros!=null && filtros.size()>0) {
			filtros.forEach(filtro -> {
				query.setParameter(filtro.getNome(), filtro.getValor());
			});
		}
		
		
		List<Model> lista = query.getResultList();
		
		for (Model model : lista) {
			this.buscaPos(model);
		}
		
		return lista;
		
	}
	
	public Model busca(Integer id) {
		return this.busca(id.longValue());
	}
	
	public Model busca(Long id) {
		
		Model usuario = this.getEm().find(this.getModelClass(), id);
		return usuario;
		
	}
	
	protected void incluirPre(Model model) throws Exception {}
	
	public Model incluir(Model model) throws Exception {
		
		this.beginTransaction();
		this.incluirPre(model);
		this.getEm().persist(model);
		return model;
		
	}
	
	protected void alterarPre(Model model) throws Exception {}
	
	public Model alterar(Model model) throws Exception {
		
		this.beginTransaction();
		this.alterarPre(model);
		this.getEm().merge(model);
		return model;
		
	}
	
	public void remover(Long id) {
		
		Model model = this.busca(id);
		this.remover(model);
		
	}
	
	public void remover(Model model) {
		
		this.beginTransaction();
		this.getEm().remove(model);
		
	}
	
	private void beginTransaction() {
		if (!this.getEm().getTransaction().isActive()) {
			this.getEm().getTransaction().begin();
		}
	}
	
	public void commit() throws Exception {
		if (this.em!=null) {
			
			if (this.em.getTransaction()!=null) {
				if (this.em.getTransaction().isActive()) {
					this.em.getTransaction().commit();
				}
			}
			this.em.close();
			
		}
	}
	
	@Override
	public void close() throws Exception {
		this.commit();
	}

	public abstract Class<Model> getModelClass();

}