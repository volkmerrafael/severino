package com.volkmer.godinho.severino.resource.jira.prioridade;

import lombok.Data;

@Data
public class Prioridade {
	
	private Long id;
	private String project_name;
	private String pkey;
	private String issueenum;
	private String summary;
	private String cliente;
	private String t;
	private String pendenteem;
	private String ano_restante;
	private String issueid;
	private String project;
	private String sitema;
	private String classificacao;
	private String interna_externa;
	private String tipo_classificacao;
	private String creator;
	private String issuetype;
	private String assignee;
	private String priorityid;
	private String resolution;
	private String issuesstatus;
	private String ano_estouro;
	private String created;
	private String updated;
	private String duedate;
	private String timespent;
	private String resolutiondate;
	private String temp_atendimento;
	private Long total_registros;
	private Long registros_atrasados;
	private Long registros_no_prazo;
	
}
