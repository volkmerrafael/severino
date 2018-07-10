package com.volkmer.godinho.severino.resource.mod_controleponto.arquivoimportacao;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.severino.entity.mod_controleponto.ArquivoImportacao;

public class ArquivoImportacaoResource extends ResourceCRUD<ArquivoImportacao> {

	public ArquivoImportacaoResource() {
	}
	
	public ArquivoImportacaoResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<ArquivoImportacao> getModelClass() {
		return ArquivoImportacao.class;
	}
	
}
