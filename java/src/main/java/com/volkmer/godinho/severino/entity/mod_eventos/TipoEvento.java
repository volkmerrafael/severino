package com.volkmer.godinho.severino.entity.mod_eventos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="TipoEvento", description="Tipo do Evento")
@Table
@Getter @Setter
@Entity
public class TipoEvento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column(length=100)
	@NotNull
	@ApiModelProperty("Descrição")
	private String descricao;
	
}