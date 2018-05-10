package com.volkmer.godinho.severino.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.importacao.ImportacaoStatus;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
public class Importacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	@Getter @Setter 
	private Long id;
	
	@Column
	@CampoInfo(descricao="Nome", obrigatorio=true)
	@Getter @Setter
	private String nome;
	
	@Column
	@CampoInfo(descricao="Tamanho", obrigatorio=true)
	@Getter @Setter
	private Integer tamanho;

	@Column
	@CampoInfo(descricao="Início Período", obrigatorio=true)
	@Getter @Setter
	private LocalDate inicio_periodo;

	@Column
	@CampoInfo(descricao="Final Período", obrigatorio=true)
	@Getter @Setter
	private LocalDate final_periodo;

	@Column
	@CampoInfo(descricao="Data e Hora Importação", obrigatorio=true)
	@Getter @Setter
	private LocalDateTime data_hora_importacao;

	@Column
	@CampoInfo(descricao="Usuários", obrigatorio=true)
	@Getter @Setter
	private Integer quantidade_usuario;

	@Column
	@CampoInfo(descricao="Usuários com Débito Banco de Horas", obrigatorio=true)
	@Getter @Setter
	private Integer usuario_com_debito_banco;

	@Column
	@CampoInfo(descricao="Usuários com Crédito Banco de Horas", obrigatorio=true)
	@Getter @Setter
	private Integer usuario_com_credito_banco;
	
	@Column
	@Enumerated(EnumType.STRING)
	@CampoInfo(descricao="Status", obrigatorio=true)
	@Getter @Setter
	private ImportacaoStatus status;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="arquivoimportacaoId", foreignKey=@ForeignKey(name="fk_arquivoimportacao_importacao"))
	@Getter @Setter
	private ArquivoImportacao arquivoimportacao;
	
}