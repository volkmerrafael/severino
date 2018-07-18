package com.volkmer.godinho.severino.entity.mod_geral.imagem;

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

@ApiModel(value="Imagem", description="Imagem Pessoa")
@Table
@Getter @Setter
@Entity
public class Imagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@NotNull
	@Column
	@ApiModelProperty("Foto")
	private byte[] foto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_imagem_usuario"))
	private Usuario usuario;
	
}
