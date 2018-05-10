package com.volkmer.godinho.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;

@Table
@Entity
public class Importacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;
	
	@Column
	@CampoInfo(descricao="Nome", obrigatorio=true)
	private String nome;
	
	@Column
	@CampoInfo(descricao="Tamanho", obrigatorio=true)
	private Integer tamanho;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="arquivoimportacaoId", foreignKey=@ForeignKey(name="fk_arquivoimportacao_importacao"))
	private ArquivoImportacao arquivoimportacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public ArquivoImportacao getArquivoimportacao() {
		return arquivoimportacao;
	}

	public void setArquivoimportacao(ArquivoImportacao arquivoimportacao) {
		this.arquivoimportacao = arquivoimportacao;
	}
	
}