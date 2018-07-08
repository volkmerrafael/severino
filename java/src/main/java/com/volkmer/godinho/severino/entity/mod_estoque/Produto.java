package com.volkmer.godinho.severino.entity.mod_estoque;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Produto", description="Produto")
@Table
@Getter @Setter
@Entity
public class Produto {
	
	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	@NotNull
	@ApiModelProperty("Descrição")
	private String descricao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JsonIgnore	
	@JoinColumn(name="vendaId", foreignKey=@ForeignKey(name="fk_produto_venda"))
	private Venda venda;
	
	//@ApiModelProperty("Compras")
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "compra")
	//private List<CompraProduto> compras = new ArrayList<>();
	
}
