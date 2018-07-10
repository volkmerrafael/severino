package com.volkmer.godinho.severino.resource.mod_eventos.tipoevento.validacao;

import java.util.List;

import javax.persistence.NoResultException;

import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.mod_eventos.TipoEvento;
import com.volkmer.godinho.severino.resource.mod_eventos.tipoevento.TipoEventoResource;

public class ValidaDuplicacaoDeRegistros {

	private RestException erroCampoNomeJaVinculadoAOutroTipoEvento = new RestException("Campo Descrição Informado já esta vinculado a outro Cadastro");
	
	public void validar(TipoEvento tipoEvento) throws Exception {
		
		try (TipoEventoResource teveRes = new TipoEventoResource()) {

			List<TipoEvento> listaTipoEvento = teveRes.buscaTotos();
			
			if (listaTipoEvento!=null && listaTipoEvento.size()>0) {
				
				for (TipoEvento teveBanco : listaTipoEvento) {
					if (teveBanco.getId()!=tipoEvento.getId()) {
						if (teveBanco.getDescricao().toUpperCase().equals(tipoEvento.getDescricao().toUpperCase())) {
							throw erroCampoNomeJaVinculadoAOutroTipoEvento.addDetalhe(teveBanco.getId()+" - "+teveBanco.getDescricao());
						}
					}
				}
				
			}
			
		} catch (NoResultException e) {
		}	
		
	}
}
