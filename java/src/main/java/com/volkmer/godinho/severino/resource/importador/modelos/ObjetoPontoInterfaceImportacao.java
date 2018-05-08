package com.volkmer.godinho.severino.resource.importador.modelos;

public interface ObjetoPontoInterfaceImportacao {
	
	public void setValor(Integer pos, Integer linha, String newValor, Object oldObject );
	public ObjetoPontoCompleto atualizar() throws Exception ;
	public Boolean isFimArquivo();
	public int getInicioArquivo();
	
}
