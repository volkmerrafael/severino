package com.volkmer.godinho.severino.entity.mod_controleponto;

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

@ApiModel(value="DiaSemana", description="Dia da Semana")
@Table(
		indexes = { 
				@Index(name = "diasemana_nome_idx", columnList = "nome")
		})
@Getter @Setter
@Entity
public class DiaSemana {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@Column(length=3)
	@NotNull
	@ApiModelProperty("Nome")
	private String nome;
			
}
