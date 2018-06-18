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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeDeserializer;
import com.volkmer.godinho.core.mapperjson.LocalDateTimeSerializer;
import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.conexao.ConexaoSistema;

import lombok.Data;

@Table
@Data
@Entity
public class Conexao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column(length=30)
	@CampoInfo(descricao="IP", obrigatorio=true)
	private String ip;
	
	@Column(length=64)
	@CampoInfo(descricao="Porta", obrigatorio=true)
	private String porta;
	
	@Column(length=128)
	@CampoInfo(descricao="Banco", obrigatorio=true)
	private String banco;

	@Column(length=128)
	@CampoInfo(descricao="Usuário", obrigatorio=true)
	private String usuario;
	
	@Column(length=128)
	@CampoInfo(descricao="Senha", obrigatorio=true)
	private String senha;
	
	@Column
	@CampoInfo(descricao="Criação da Conexão", obrigatorio=true)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using =  LocalDateTimeSerializer.class)
	private LocalDateTime data;
	
	@Column
	@Enumerated(EnumType.STRING)
	@CampoInfo(descricao="Conexão Sistema", obrigatorio=true)
	private ConexaoSistema conexao_sistema;
}
