package com.volkmer.godinho.severino.resource.importador;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.volkmer.godinho.severino.entity.Importacao;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPonto;
import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPontoInterfaceImportacao;
import com.volkmer.godinho.severino.resource.importador.utils.UtilsImportacao;


public class ConverteExcelEmObjetoPonto {
	
	public static String conteudoCelula; 
	public int linhasArqExel;
	public int colunasArqExel;
	
	public static final int Ponto9 = 1;
	
	List<ObjetoPontoInterfaceImportacao> listGravar;
	
	List<Ponto> listaFinal = new ArrayList<Ponto>();
	
	public ObjetoPontoInterfaceImportacao createModeloImportacao() {
		return new ObjetoPonto();	
	}
	
	public List<Ponto> importacao(Importacao importacao) throws Exception {

		try {	
			
			HSSFWorkbook arqExel = null;

			String extensaoArq = importacao.getExtensao();
			
			File file = new File(importacao.getNome()+".xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(importacao.getArquivoimportacao().getAnexo()); //Gravamos os bytes lá
			bos.close(); //Fechamos o stream.
			
			FileInputStream fileIn = new FileInputStream(file);
			
			if (extensaoArq.equals(".xls")) {
	        	 try {
	                 //Metodo nao aceita string do path do arquivo
	        		 arqExel = new HSSFWorkbook(fileIn);
	             } catch (IOException ex) {
	            	 throw ex;
	             } 
	         }

			ObjetoPontoInterfaceImportacao oldModelo = this.createModeloImportacao();
			
			for (int pagina = 0; pagina < arqExel.getNumberOfSheets(); pagina++) {
				
				listGravar = new ArrayList<ObjetoPontoInterfaceImportacao>();
				
				HSSFSheet hssfSheet = arqExel.getSheetAt(pagina);
				
				int i = this.createModeloImportacao().getInicioArquivo();
							
				while (!this.createModeloImportacao().isFimArquivo() && i<=hssfSheet.getLastRowNum()) {
				
					HSSFRow hssfRow = hssfSheet.getRow(i);
					
					HSSFCell cell;
					colunasArqExel = hssfRow.getLastCellNum();
					ObjetoPontoInterfaceImportacao modelo = this.createModeloImportacao();
					
					for (int j=0; j < colunasArqExel; j++) {
						cell = hssfRow.getCell(j);
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
					
					i++;
					
				}
				//Fim da conversão do arquivo em Objeto
				
				//Percore os itens do modelo, quais foram adicionados na lista para gravando de acordo com as regras de cada modelo.
				for ( ObjetoPontoInterfaceImportacao item : listGravar ) {
					
					Ponto ponto = (Ponto) item.atualizar( );
					if (ponto!=null && ponto.getData()!=null) {
						if (!listaFinal.contains(ponto)) {
							listaFinal.add(ponto);
						}
					}
				}
					
			}
			
			return listaFinal;
			
		} catch (Exception e ) {
			throw e;
		}
	}

	
}
