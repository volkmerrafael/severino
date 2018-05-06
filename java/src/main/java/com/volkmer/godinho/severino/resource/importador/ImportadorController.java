package com.volkmer.godinho.severino.resource.importador;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPontoCompleto;

@Path("/importacao")
public class ImportadorController {

	@HeaderParam("user-token")
	String userToken;

	@GET
	@Path("/fazer")
	@Produces(MediaType.APPLICATION_JSON)
	public void fazer() throws Exception {
	
		//Etapas
		
		//Converte Arquivo em Lista de Objetos
		
		List<ObjetoPontoCompleto> listaRetorno = new ConverteExcelEmObjetoPonto().importacao();
		
		List<ObjetoPontoCompleto> listaFinal = new ArrayList<ObjetoPontoCompleto>();
		
		//remove itens desnecessarios da lista
		if (listaRetorno!=null && listaRetorno.size()>0) {
			for (ObjetoPontoCompleto objetoPontoCompleto : listaRetorno) {
				if (objetoPontoCompleto.getPonto().getData()!=null 
						&& objetoPontoCompleto.getPonto().getData()!="" 
						&& objetoPontoCompleto.getPonto().getDiasemana()!=null
						&& objetoPontoCompleto.getPonto().getDiasemana()!="") {
					
					//Faz calculos e processa as linhas
					new ProcessaDadosPonto().processar(objetoPontoCompleto);
					
					listaFinal.add(objetoPontoCompleto);
				}
			}
		}
		
		
		for (ObjetoPontoCompleto obj : listaFinal) {
			
			new ImportadorResource().gravarPonto(obj);
			
		}
		
	}
	
}
