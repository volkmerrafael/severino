package com.volkmer.godinho.severino.entity.mod_estoque;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Produto", description="produto")
@Table
@Getter @Setter
@Entity
public class CompraProduto {
	
	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ApiModelProperty("Produto")
	@ManyToOne(optional = false)
	@JoinColumn(name="produtoid")
	private Produto produto;
	
	@ApiModelProperty("Compra")
	@ManyToOne(optional = false)
	@JoinColumn(name="compraid")
	private Compra compra;

}
