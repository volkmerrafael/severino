package com.volkmer.godinho.severino.resource.mod_jira.prioridade;

import java.util.List;

import lombok.Data;

@Data
public class PrioridadesInfo {
	
	private Integer total_registros;
	private Integer registros_atrasados;
	private Integer registros_no_prazo;
	private List<Prioridade> lista_prioridades;
	
}