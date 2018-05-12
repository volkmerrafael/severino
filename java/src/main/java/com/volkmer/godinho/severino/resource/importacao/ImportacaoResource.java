package com.volkmer.godinho.severino.resource.importacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.timmer.ControleTempoDeExecucao;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.Importacao;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.importador.ConverteExcelEmObjetoPonto;
import com.volkmer.godinho.severino.resource.importador.ImportadorResource;
import com.volkmer.godinho.severino.resource.importador.ProcessaDadosPonto;
import com.volkmer.godinho.severino.resource.importador.modelos.ObjetoPontoCompleto;
import com.volkmer.godinho.severino.resource.ponto.PontoStatus;

public class ImportacaoResource extends ResourceCRUD<Importacao> {

	public ImportacaoResource() {
	}
	
	public ImportacaoResource(ResourceCRUD<?> res) {
		super(res);
	}
	
	@Override
	public Class<Importacao> getModelClass() {
		return Importacao.class;
	}

	public List<Importacao> listarImportacoes(String userToken) {
		
		if (this.ehUsarioAdmin(userToken)) {
			TypedQuery<Importacao> queryImportacao = this.getEm().createQuery("select i from Importacao i order by data_hora_importacao desc", Importacao.class);
			List<Importacao> lista = queryImportacao.getResultList();
			return lista;
		}
		
		return null;
		
	}
	
	public Importacao gravar(String userToken, Importacao importacao) throws Exception {
		
		if (this.ehUsarioAdmin(userToken)) {
		
			//Classe que controla o tempo que leva pra executar a importacão
			ControleTempoDeExecucao cte = new ControleTempoDeExecucao();
			//Informa Início
			cte.inicio();
			
			//Antes de gravar seta a situação Pendente na importação
			importacao.setStatus(ImportacaoStatus.PENDENTE);
			//Inclui arquivo no banco de dados
			Importacao importacaoGravada = this.incluir(importacao);
			this.incluir(importacaoGravada);
			this.commit();
						
			for (ObjetoPontoCompleto obj : this.retornaListaFinal(importacaoGravada)) {
				//Grava Registro do ponto e Vincula a Impotação
				new ImportadorResource().gravarAlterarPonto(obj,userToken,importacaoGravada);

				//Seta período que esta no arquivo
				if (importacaoGravada.getInicio_periodo()!=null) {
					importacaoGravada.setInicio_periodo(obj.getData_inicial_importacao());
				}
				if (importacaoGravada.getFinal_periodo()!=null) {
					importacaoGravada.setFinal_periodo(obj.getData_final_importacao());
				}
	
			}
			
			this.calcularEstatisticasDoArquivo(importacaoGravada);	
			
			//Grava no banco o tempo que demorou para importar o arquivo
			importacaoGravada.setTempo_importacao(cte.fim());
			this.alterar(importacaoGravada);
			this.commit();
			
		}
		
		return null;
		
	}
	
	private void calcularEstatisticasDoArquivo(Importacao importacaoGravada) {
		
		importacaoGravada.setData_hora_importacao(LocalDateTime.now());
		importacaoGravada.setQuantidade_usuario(this.contaUsuarios(importacaoGravada,null));
		importacaoGravada.setUsuario_com_credito_banco(this.contaUsuarios(importacaoGravada,PontoStatus.CORRETO));
		importacaoGravada.setUsuario_com_credito_banco(this.contaUsuarios(importacaoGravada,PontoStatus.CREDITO));
		importacaoGravada.setUsuario_com_debito_banco(this.contaUsuarios(importacaoGravada,PontoStatus.DEBITO));
		importacaoGravada.setUsuario_com_marcacao_incorreta(this.contaUsuarios(importacaoGravada,PontoStatus.MARCACAO_INCORRETA));
		importacaoGravada.setStatus(ImportacaoStatus.CONCLUIDO);
		
	}

	private Integer contaUsuarios(Importacao importacao, PontoStatus pontoStatus) {
		TypedQuery<Usuario> queryPonto = this.getEm().createQuery("select u from Usuario u where u.id in (select usuarioid from Ponto where p.importacao = :importacao and (:status is null or status = :status))", Usuario.class);
		queryPonto.setParameter("importacao", importacao);
		queryPonto.setParameter("status", pontoStatus);
		List<Usuario> lista = queryPonto.getResultList();
		return lista.size();
	}

	private boolean ehUsarioAdmin(String userToken) {
		
		TypedQuery<Acesso> queryAcesso = this.getEm().createQuery("select a from Acesso a where a.token = :token", Acesso.class);
		queryAcesso.setParameter("token", userToken);
		Acesso acesso = queryAcesso.getSingleResult();
		
		if (acesso.getTipo().equals(AcessoTipo.ADMIN)) {
			return true;
		}		
		
		return false;
		
	}
	
	private List<ObjetoPontoCompleto> retornaListaFinal(Importacao importacaoGravada) throws Exception {
		
		//Converte Arquivo em Lista de Objetos
		List<ObjetoPontoCompleto> listaRetorno = new ConverteExcelEmObjetoPonto().importacao(importacaoGravada);
		
		List<ObjetoPontoCompleto> listaFinal = new ArrayList<ObjetoPontoCompleto>();
		
		//remove itens desnecessarios da lista
		if (listaRetorno!=null && listaRetorno.size()>0) {
			for (ObjetoPontoCompleto objetoPontoCompleto : listaRetorno) {
				if (objetoPontoCompleto.getPonto().getData()!=null 
						&& objetoPontoCompleto.getPonto().getDiasemana()!=null
						&& objetoPontoCompleto.getPonto().getDiasemana()!="") {
					
					//Faz calculos e processa as linhas
					new ProcessaDadosPonto().processar(objetoPontoCompleto);
					
					listaFinal.add(objetoPontoCompleto);
				}
			}
		}
		
		return listaFinal;

	}

	public List<Usuario> listarUsuarios(String userToken, Integer importacao, Integer status) {
		
		if (this.ehUsarioAdmin(userToken)) {
			TypedQuery<Usuario> queryPonto = this.getEm().createQuery("select u from Usuario u where u.id in (select usuarioid from Ponto where p.importacao = :importacao and (:status is null or status = :status))", Usuario.class);
			queryPonto.setParameter("importacao", importacao);
			queryPonto.setParameter("status", status);
			List<Usuario> lista = queryPonto.getResultList();
			
			return lista;
		}
		return null;
	}
	
}