package com.volkmer.godinho.severino.resource.mod_controleponto.importador.modelos;

import com.volkmer.godinho.severino.entity.mod_controleponto.Ponto;

public interface ObjetoPontoInterfaceImportacao {
	
	public void setValor(Integer pos, Integer linha, String newValor, Object oldObject );
	public Ponto atualizar() throws Exception ;
	public Boolean isFimArquivo();
	public int getInicioArquivo();
	
}
