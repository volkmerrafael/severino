package com.volkmer.godinho.severino.entity.mod_jira;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volkmer.godinho.severino.entity.mod_controleponto.Ponto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Issue", description="Issue")
@Table
@Getter @Setter
@Entity
public class Issue {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@ApiModelProperty("Código")
		private Long id;

		@Column(length=500)
		@NotNull
		@ApiModelProperty("Código da Issue")
		private String issue;
		
		@Column(length=500)
		@NotNull
		@ApiModelProperty("Descrição da Issue")
		private String summary;
		
		@ManyToOne(fetch=FetchType.EAGER)
		@JsonIgnore	
		@JoinColumn(name="pontoId", foreignKey=@ForeignKey(name="fk_ponto_issue"))
		private Ponto ponto;
		
}