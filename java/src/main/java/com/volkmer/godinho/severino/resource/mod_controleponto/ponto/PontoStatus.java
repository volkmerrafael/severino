package com.volkmer.godinho.severino.resource.mod_controleponto.ponto;

import com.volkmer.godinho.core.util.EnumTransformacao;

public enum PontoStatus implements EnumTransformacao {
	
	CORRETO("Correto"),
	MARCACAO_INCORRETA("Marcação Incorreta"),
	DEBITO("Débito"),
	CREDITO("Crédito"),
	JUSTIFICADO("Justificado"),
	SEM_INFORMACAO("Sem Informação"),
	FERIAS("Férias"),
	ATESTADO_MEDICO("Atestado Médico"),
	PONTO_FACULTATIVO("Ponto Facultativo"),
	FALTA_JUSTIFICADA("Falta Justificada"),
	NAO_ADMITIDO("Não Admitido"),
	FERIADO("Feriado"),
	CERTIDAO_DE_OBITO("Certidão de Óbito"),
	COMPENSACAO("Compensação");

	private String nome_formatado;

	private PontoStatus(String nome_formatado) {
		this.nome_formatado = nome_formatado;
	}

	@Override
	public String getDescricao() {
		return this.nome_formatado;
	}
	
}
