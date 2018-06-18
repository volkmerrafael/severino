package com.volkmer.godinho.severino.resource.importador.modelos;

import com.volkmer.godinho.severino.entity.Ponto;

public interface ObjetoPontoInterfaceImportacao {
	
	public void setValor(Integer pos, Integer linha, String newValor, Object oldObject );
	public Ponto atualizar() throws Exception ;
	public Boolean isFimArquivo();
	public int getInicioArquivo();
	
}
