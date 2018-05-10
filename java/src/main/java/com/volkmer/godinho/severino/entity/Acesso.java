package com.volkmer.godinho.severino.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
public class Acesso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	@Getter @Setter
	private Long id;
	
	@Column(length=30)
	@CampoInfo(descricao="Nome", obrigatorio=true)
	@Getter @Setter
	private String nomeacesso;
	
	@Column(length=64)
	@CampoInfo(descricao="Senha", obrigatorio=true)
	@Getter @Setter
	private String senha;
	
	@Column
	@CampoInfo(descricao="Data", obrigatorio=true)
	@Getter @Setter
	private LocalDateTime data;
	
	@Column(length=128)
	@CampoInfo(descricao="Chave do Usuário", obrigatorio=true)
	@Getter @Setter
	private String token;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	@Getter @Setter
	private AcessoTipo tipo;
	
}
