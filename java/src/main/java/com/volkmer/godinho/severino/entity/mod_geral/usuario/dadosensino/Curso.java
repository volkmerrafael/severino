package com.volkmer.godinho.severino.entity.mod_geral.usuario.dadosensino;

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
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Curso", description="Curso")
@Table
@Getter @Setter
@Entity
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@NotNull
	@Column(length=300)
	@ApiModelProperty("Descrição")
	private String descricao;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="instituicaoId", foreignKey=@ForeignKey(name="fk_curso_instituicao"))
	private Instituicao instituicao;
	
}
