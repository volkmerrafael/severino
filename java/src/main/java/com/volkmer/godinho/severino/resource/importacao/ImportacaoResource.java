package com.volkmer.godinho.severino.resource.importacao;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.Importacao;

public class ImportacaoResource extends ResourceCRUD<Importacao> {

	public ImportacaoResource() {
	}
	
	public ImportacaoResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<Importacao> getModelClass() {
		return Importacao.class;
	}
	
}