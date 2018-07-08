package com.volkmer.godinho.severino.entity.mod_controleponto;

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

import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="ControleHoras", description="Controle de Horas")
@Table
@Getter @Setter
@Entity
public class ControleHoras {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_controlehoras_usuario"))
	private Usuario usuario;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="anomesId", foreignKey=@ForeignKey(name="fk_controlehoras_anomes"))
	private AnoMes anomes;
		
	@Column
	@ApiModelProperty("Horas Crédito")
	private Integer Credito;
	
	@Column
	@ApiModelProperty("Horas Saldo do Mês")
	private Integer saldo;
	
	@Column
	@ApiModelProperty("Horas de Débito")
	private Integer debito;

	@Column
	@ApiModelProperty("Horas de Abono")
	private Integer abono;
	
	@Column
	@ApiModelProperty("Horas Trabalhadas")
	private Integer trabalhadas;
	
	@Column
	@ApiModelProperty("Banco de Horas")
	private Integer banco_de_horas;
	
	@Column
	@ApiModelProperty("Percentual % de Absenteismo")
	private BigDecimal absenteismo;

}
