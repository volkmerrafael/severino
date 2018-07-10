package com.volkmer.godinho.severino.entity.mod_estoque;

import java.time.LocalDate;
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

@ApiModel(value="Venda", description="Venda")
@Table
@Getter @Setter
@Entity
public class Venda {
	
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
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_venda_usuario"))
	private Usuario usuario;
	
	@ApiModelProperty("Produtos")
	@OneToMany(mappedBy = "venda", fetch = FetchType.EAGER) //, cascade = CascadeType.ALL
	private List<Produto> produtos;
	
}
