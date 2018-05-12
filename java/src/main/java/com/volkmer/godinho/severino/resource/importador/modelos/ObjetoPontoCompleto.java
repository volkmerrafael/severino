package com.volkmer.godinho.severino.resource.importador.modelos
;
import java.time.LocalDate;

import com.volkmer.godinho.severino.entity.Ponto;

import lombok.Data;

@Data
public class ObjetoPontoCompleto {
	
	private String funcionario;
	private String pis;
	private LocalDate data_admissao;
	private String funcao;
	private String departamento;
	private Ponto ponto;
	private LocalDate data_inicial_importacao;
	private LocalDate data_final_importacao;
	
}