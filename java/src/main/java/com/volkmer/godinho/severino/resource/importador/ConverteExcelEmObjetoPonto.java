package com.volkmer.godinho.severino.resource.importador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPonto;
import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPontoCompleto;
import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPontoInterfaceImportacao;
import com.volkmer.godinho.severino.resource.importador.utils.UtilsImportacao;


public class ConverteExcelEmObjetoPonto {
	
	int i = 0;
	public static String conteudoCelula; 
	public int linhasArqExel;
	public int colunasArqExel;
	
	public static final int Ponto9 = 1;
	
	List<ObjetoPontoInterfaceImportacao> listGravar;
	
	List<ObjetoPontoCompleto> listaFinal = new ArrayList<ObjetoPontoCompleto>();
	
	public ObjetoPontoInterfaceImportacao createModeloImportacao() {
		return new ObjetoPonto();	
	}
	
	public List<ObjetoPontoCompleto> importacao() throws Exception {

		try {	
			
			// Caminho do arquivo excel de ponto
			String caminho = "D:\\repositorio\\severino\\excelponto\\Ponto.xls";
			
			Workbook arqExel = null;

			String nomeArquivo = caminho;
			int pos = nomeArquivo.lastIndexOf('.');
			String extensaoArq = nomeArquivo.substring(pos);
			
			File file = new File(caminho);
			FileInputStream fileIn = new FileInputStream(file);
			
	        if (extensaoArq.equals(".xlsx")) {
	        	try {
	                //Metodo aceita o path do arquivo
	        		arqExel = new XSSFWorkbook(fileIn);
	            } catch (IOException ex) {
	                throw ex;
	            } 
	         } else if (extensaoArq.equals(".xls")) {
	        	 try {
	                 //Metodo nao aceita string do path do arquivo
	        		 arqExel = new HSSFWorkbook(fileIn);
	             } catch (IOException ex) {
	            	 throw ex;
	             } 
	         }

			ObjetoPontoInterfaceImportacao oldModelo = this.createModeloImportacao();
			
			//Lista de Retorno Final
			listGravar = new ArrayList<ObjetoPontoInterfaceImportacao>();

			for (int pagina = 0; pagina < arqExel.getNumberOfSheets(); pagina++) {
				
				//Abas do Planilha - Sheet 
		        Sheet sheet = arqExel.getSheetAt(pagina);
		        
				//Busca o total de linhas do arquivo Exel
				linhasArqExel  = sheet.getLastRowNum()+1;
				
				if (linhasArqExel > 0) {	
					//Inicio conversão do arquivo em Objeto
					
					for (i = (this.createModeloImportacao().getInicioArquivo()); i < linhasArqExel; i++) {
						Row row = sheet.getRow(i);
						Cell cell;
						colunasArqExel = row.getLastCellNum();
						ObjetoPontoInterfaceImportacao modelo = this.createModeloImportacao();
						
						for (int j=0; j < colunasArqExel; j++) {
							cell = row.getCell(j);
							if (cell!=null) {
								if (cell.getCellType()==0) {
									//conteudoCelula = ("" + cell.getNumericCellValue()).replace(".0", "");
									conteudoCelula = String.valueOf(cell.getNumericCellValue());
								} else {
									conteudoCelula = cell.getStringCellValue().toString();
								}
								modelo.setValor( j, i, conteudoCelula, oldModelo );
							}
							
						}
						
						if ( modelo.isFimArquivo() ) {
							break;
						} else {
							listGravar.add( modelo );
							oldModelo = this.createModeloImportacao();
							UtilsImportacao.copiaPropriedadesGeneric( modelo, oldModelo );
						}
						
						
					}
					//Fim da conversão do arquivo em Objeto
					
					//Percore os itens do modelo, quais foram adicionados na lista para gravando de acordo com as regras de cada modelo.
					for ( ObjetoPontoInterfaceImportacao item : listGravar ) {
						
						ObjetoPontoCompleto objetoCompletoPonto = (ObjetoPontoCompleto) item.atualizar( );
						listaFinal.add(objetoCompletoPonto);
						
					}
					
				}
			}
			
			return listaFinal;
			
		} catch (Exception e ) {
			throw e;
		}
	}

	
}
