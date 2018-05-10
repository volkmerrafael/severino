package com.volkmer.godinho.severino.entity;

import java.time.LocalDate;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.volkmer.godinho.core.validacao.CampoInfo;
import com.volkmer.godinho.severino.resource.ponto.PontoStatus;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
public class Ponto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	@Getter @Setter
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_ponto_usuario"))
	@Getter @Setter
	private Usuario usuario;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="anomesId", foreignKey=@ForeignKey(name="fk_ponto_anomes"))
	@Getter @Setter
	private AnoMes anomes;
	
	@Column
	@CampoInfo(descricao="Data", obrigatorio=false)
	@Getter @Setter
	private LocalDate data;
	
	@Column(length=3)
	@CampoInfo(descricao="Dia da Semana", obrigatorio=false)
	@Getter @Setter
	private String diasemana;
	
	@Column(length=26)
	@CampoInfo(descricao="Jornada", obrigatorio=false)
	@Getter @Setter
	private String jornada;

	@Column(length=1)
	@CampoInfo(descricao="Legenda", obrigatorio=false)
	@Getter @Setter
	private String legenda;

	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 1", obrigatorio=false)
	@Getter @Setter
	private String entrada1;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 1", obrigatorio=false)
	@Getter @Setter
	private String saida1;

	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 2", obrigatorio=false)
	@Getter @Setter
	private String entrada2;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 2", obrigatorio=false)
	@Getter @Setter
	private String saida2;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 3", obrigatorio=false)
	@Getter @Setter
	private String entrada3;

	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 3", obrigatorio=false)
	@Getter @Setter
	private String saida3;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Entrada 4", obrigatorio=false)
	@Getter @Setter
	private String entrada4;
	
	@Column(length=6)
	@CampoInfo(descricao="Horário de Saída 4", obrigatorio=false)
	@Getter @Setter
	private String saida4;
	
	@Column(length=150)
	@CampoInfo(descricao="Horas Trabalhadas Diurno", obrigatorio=false)
	@Getter @Setter
	private String trabalhadas_diurno;

	@Column(length=150)
	@CampoInfo(descricao="Horas Trabalhadas Noturno", obrigatorio=false)
	@Getter @Setter
	private String trabalhadas_noturno;
	
	@Column(length=150)
	@CampoInfo(descricao="Horas Extra Diurno", obrigatorio=false)
	@Getter @Setter
	private String extra_diurno;

	@Column(length=150)
	@CampoInfo(descricao="Horas Extra Noturno", obrigatorio=false)
	@Getter @Setter
	private String extra_noturno;

	@Column(length=150)
	@CampoInfo(descricao="Horas Falta Diurno", obrigatorio=false)
	@Getter @Setter
	private String faltas_diurno;

	@Column(length=150)
	@CampoInfo(descricao="Horas Falta Noturno", obrigatorio=false)
	@Getter @Setter
	private String faltas_noturno;

	@Column
	@Enumerated(EnumType.STRING)
	@CampoInfo(descricao="Status", obrigatorio=true)
	@Getter @Setter
	private PontoStatus status;

	@Column
	@CampoInfo(descricao="Minutos Débito", obrigatorio=true)
	@Getter @Setter
	private Integer minutos_debito;

	@Column
	@CampoInfo(descricao="Minutos Crédito", obrigatorio=true)
	@Getter @Setter
	private Integer minutos_credito;
	
	@Column(length=150)
	@CampoInfo(descricao="Observação", obrigatorio=false)
	@Getter @Setter
	private String observacao;

}