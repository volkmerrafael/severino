package com.volkmer.godinho.severino.entity;

import java.time.LocalDateTime;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
public class Sessao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acessoId", foreignKey=@ForeignKey(name="fk_Sessao_Acesso"))
	@Getter @Setter
	private Acesso acesso;
	
	@Column
	@Getter @Setter
	private LocalDateTime data;
	
	@Column(length=50)
	@Getter @Setter
	private String ip;
	
	@Column(columnDefinition="text")
	@Getter @Setter
	private String useragent;
	
	@Column(length=128)
	@Getter @Setter
	private String token;

}
