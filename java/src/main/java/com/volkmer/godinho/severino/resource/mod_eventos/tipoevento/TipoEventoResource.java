package com.volkmer.godinho.severino.resource.mod_eventos.tipoevento;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_eventos.TipoEvento;
import com.volkmer.godinho.severino.resource.mod_eventos.tipoevento.validacao.ValidaDuplicacaoDeRegistros;


public class TipoEventoResource extends ResourceCRUD<TipoEvento> {
	
	public TipoEventoResource() {
	}
	
	@Override
	public Class<TipoEvento> getModelClass() {
		return TipoEvento.class;
	}
		
	@Override
	protected void alterarPre(TipoEvento model) throws Exception {
		
		new ValidaDuplicacaoDeRegistros().validar(model);
	
	}
	
	@Override
	protected void incluirPre(TipoEvento model) throws Exception {
		new ValidaDuplicacaoDeRegistros().validar(model);
	}
	
}