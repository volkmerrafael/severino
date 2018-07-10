package com.volkmer.godinho.severino.entity.mod_geral;

import java.math.BigDecimal;

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
	
	@Column(length=150)
	@ApiModelProperty("E-mail de notificação")
	private String email_notificacao;

	@Column(length=100)
	@ApiModelProperty("Host de E-mail")
	private String email_host;
	
	@Column
	@ApiModelProperty("SMTP Port de E-mail")
	private Integer email_smtp_port;
	
	@Column(length=100)
	@ApiModelProperty("Senha do E-mail")
	private String email_senha;
	
	@Column(length=300)
	@ApiModelProperty("Chave api Google")
	private String apkey_google;
	
	@Column
	@ApiModelProperty("Latitude Padrão")
	private BigDecimal latitude;

	@Column
	@ApiModelProperty("Longitude Padrão")
	private BigDecimal longitude;
	
}
