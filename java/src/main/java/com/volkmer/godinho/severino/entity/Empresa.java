package com.volkmer.godinho.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table(
		indexes = { 
				@Index(name = "empresa_razaosocial_idx", columnList = "razao_social")
		})
@Data
@Entity
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column(length=300)
	@CampoInfo(descricao="Razão Social", obrigatorio=true)
	private String razao_social;
	
	@Column(length=200)
	@CampoInfo(descricao="CNPJ", obrigatorio=true)
	private String cnpj;

	@Column(length=200)
	@CampoInfo(descricao="Endereço", obrigatorio=true)
	private String endereco;
	
	@Column(length=200)
	@CampoInfo(descricao="Cidade", obrigatorio=true)
	private String cidade;
	
	@Column(length=200)
	@CampoInfo(descricao="Uf", obrigatorio=true)
	private String uf;
	
}
