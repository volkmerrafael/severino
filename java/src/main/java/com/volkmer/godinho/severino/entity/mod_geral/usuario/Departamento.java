package com.volkmer.godinho.severino.entity.mod_geral.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Departamento")
@Table(
		indexes = { 
				@Index(name = "departamento_nome_idx", columnList = "nome")
		})
@Getter @Setter
@Entity
public class Departamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@NotNull
	@Column(length=200)
	@ApiModelProperty("Nome")
	private String nome;
	
	@Column(length=200)
	@ApiModelProperty("Tag Sistema Jira")
	private String tags_sistema_jira;
	
}
