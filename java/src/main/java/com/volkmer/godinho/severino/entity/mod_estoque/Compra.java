package com.volkmer.godinho.severino.entity.mod_estoque;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Compra", description="Compra")
@Table
@Getter @Setter
@Entity
public class Compra {
	
	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@ApiModelProperty("Data")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate data;
	
	@ApiModelProperty("Usuário")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioid", foreignKey=@ForeignKey(name="fk_compra_usuario"))
	private Usuario usuario;
	
	@ApiModelProperty("Fornecedor")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fornecedorId", foreignKey=@ForeignKey(name="fk_compra_fornecedor"))
	private Fornecedor fornecedor;
	
	@ApiModelProperty("Produtos")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "produto")
	private List<CompraProduto> produtos = new ArrayList<>();
}
