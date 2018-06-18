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

import com.volkmer.godinho.core.validacao.CampoInfo;

import lombok.Data;

@Table
@Data
@Entity
public class ControleHoras {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoInfo(descricao="Código")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_controlehoras_usuario"))
	private Usuario usuario;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="anomesId", foreignKey=@ForeignKey(name="fk_controlehoras_anomes"))
	private AnoMes anomes;
		
	@Column
	@CampoInfo(descricao="Crédito", obrigatorio=false)
	private Integer Credito;
	
	@Column
	@CampoInfo(descricao="Saldo Mês", obrigatorio=false)
	private Integer saldo;
	
	@Column
	@CampoInfo(descricao="Débito", obrigatorio=false)
	private Integer debito;

	@Column
	@CampoInfo(descricao="Abono", obrigatorio=false)
	private Integer abono;
	
	@Column
	@CampoInfo(descricao="Trabalhadas", obrigatorio=false)
	private Integer trabalhadas;
	
	@Column
	@CampoInfo(descricao="Banco de Horas", obrigatorio=false)
	private Integer banco_de_horas;
	
	@Column
	@CampoInfo(descricao="Absenteismo", obrigatorio=false)
	private BigDecimal absenteismo;

}
