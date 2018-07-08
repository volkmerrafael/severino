package com.volkmer.godinho.severino.entity.mod_geral;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Configuracao", description="Configuração")
@Table
@Getter @Setter
@Entity
public class Configuracao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column
	@ApiModelProperty("Integração Jira")
	private Boolean integra_jira = false;
	
	@Column
	@ApiModelProperty("Integração Slack")
	private Boolean integra_slack = false;
	
}
