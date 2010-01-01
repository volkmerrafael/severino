package com.volkmer.godinho.severino.entity;

import java.time.LocalDate;

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

import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table
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
	
	@Column
	@CampoInfo(descricao="Data Admissão", obrigatorio=false)
	private LocalDate data_admissao;
	
	@Column(length=26)
	@CampoInfo(descricao="P.I.S", obrigatorio=false)
	private String pis;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acessoId", foreignKey=@ForeignKey(name="fk_usuario_acesso"))
	private Acesso acesso;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="departamentoId", foreignKey=@ForeignKey(name="fk_usuario_departamento"))
	private Departamento departamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="funcaoId", foreignKey=@ForeignKey(name="fk_usuario_funcao"))
	private Funcao funcao;
		
}
