
package com.volkmer.godinho.severino.entity;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table(
		indexes = { 
				@Index(name = "usuario_pis_idx", columnList = "pis")
		})
@Data
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column(length=200)
	@CampoInfo(descricao="Nome", obrigatorio=true)
	private String nome;
	
	@Column(length=200)
	@CampoInfo(descricao="Email", obrigatorio=true)
	private String email;
	
	@Column(length=200)
	@CampoInfo(descricao="Usuário Jira", obrigatorio=true)
	private String usuario_jira;
	
	@Column
	@CampoInfo(descricao="Data Admissão", obrigatorio=false)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate data_admissao;
	
	@Column(length=26)
	@CampoInfo(descricao="P.I.S", obrigatorio=false)
	private Long pis;
	
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
