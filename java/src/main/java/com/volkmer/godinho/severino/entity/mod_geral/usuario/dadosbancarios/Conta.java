package com.volkmer.godinho.severino.entity.mod_geral.usuario.dadosbancarios;

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

import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Conta", description="Conta")
@Table
@Getter @Setter
@Entity
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@NotNull
	@Column(length=300)
	@ApiModelProperty("Descrição")
	private String descricao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="agenciaId", foreignKey=@ForeignKey(name="fk_conta_agencia"))
	private Agencia agencia;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_conta_usuario"))
	private Usuario usuario;
	
}
