package com.volkmer.godinho.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table
@Data
@Entity
public class Configuracao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column
	@CampoInfo(descricao="Integração Jira", obrigatorio=true)
	private Boolean integra_jira = false;
	
	@Column
	@CampoInfo(descricao="Integração Slack", obrigatorio=true)
	private Boolean integra_slack = false;
	
}
