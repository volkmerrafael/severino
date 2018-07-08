
package com.volkmer.godinho.severino.entity.mod_geral.usuario;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.volkmer.godinho.severino.entity.mod_acesso.Acesso;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Usuario", description="Usuário")
@Table(
		indexes = { 
				@Index(name = "usuario_pis_idx", columnList = "pis")
		})
@Getter @Setter
@Entity
public class Usuario {
	
	@Id
	@ApiModelProperty("Código")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=200)
	@NotNull
	@ApiModelProperty("Nome")
	private String nome;
	
	@Column(length=200)
	@NotNull
	@ApiModelProperty("E-mail")
	private String email;
	
	@Column(length=200)
	@ApiModelProperty("Usuário Jira")
	private String usuario_jira;
	
	@Column
	@ApiModelProperty("Data Admissão")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate data_admissao;
	
	@Column(length=26)
	@ApiModelProperty("P.I.S.")
	private Long pis;
	
	@Column(length=11)
	@ApiModelProperty("C.P.F.")
	private String cpf;
	
	@Column(length=20)
	@ApiModelProperty("R.G.")
	private String rg;

	@Column(length=11)
	@ApiModelProperty("Telefône")
	private String telefone;
	
	@Column(length=11)
	@ApiModelProperty("Celular")
	private String celular;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="acessoId", foreignKey=@ForeignKey(name="fk_usuario_acesso"))
	private Acesso acesso;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="departamentoId", foreignKey=@ForeignKey(name="fk_usuario_departamento"))
	private Departamento departamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="funcaoId", foreignKey=@ForeignKey(name="fk_usuario_funcao"))
	private Funcao funcao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="empresaId", foreignKey=@ForeignKey(name="fk_usuario_empresa"))
	private Empresa empresa;
	
}
