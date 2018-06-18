package com.volkmer.godinho.severino.entity;

import java.math.BigDecimal;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Getter;
import lombok.Setter;

@Table
@Getter @Setter
@Entity
public class Worklog {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@CampoInfo(descricao="Código")
		private Long id;

		@Column(length=500)
		@CampoInfo(descricao="Descrição da Issue", obrigatorio=false)
		private String issue;
		
		@Column(length=120)
		@CampoInfo(descricao="Autor", obrigatorio=false)
		private String author;
		
		@Column(length=100)
		@CampoInfo(descricao="Inicio do Trabalho na Issue", obrigatorio=false)
		private String startdate;
		
		@Column(length=500)
		@CampoInfo(descricao="Descrição da Issue", obrigatorio=false)
		private String summary;
		
		@Column
		@CampoInfo(descricao="Tempo de Trabalho na Issue", obrigatorio=false)
		private BigDecimal timeworked;
		
		@Column
		@CampoInfo(descricao="Issue gravada", obrigatorio=false)
		private Boolean gravada = false;
		
		@ManyToOne(fetch=FetchType.EAGER)
		@JsonIgnore	
		@JoinColumn(name="pontoId", foreignKey=@ForeignKey(name="fk_ponto_issue"))
		private Ponto ponto;
		
}