package com.volkmer.godinho.severino.entity.mod_acesso;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Sessao", description="Sessão")
@Entity
@Getter @Setter
@Table
public class Sessao {

	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acessoId", foreignKey=@ForeignKey(name="fk_Sessao_Acesso"))
	private Acesso acesso;
	
	@Column
	@ApiModelProperty("Data e Hora")
	private LocalDateTime data;
	
	@Column(length=50)
	@ApiModelProperty("IP")
	private String ip;
	
	@Column(columnDefinition="text")
	@ApiModelProperty("User Agent")
	private String useragent;
	
	@Column(length=128)
	@ApiModelProperty("Token")
	private String token;

}
