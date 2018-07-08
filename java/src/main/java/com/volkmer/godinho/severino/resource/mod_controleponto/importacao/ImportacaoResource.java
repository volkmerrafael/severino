package com.volkmer.godinho.severino.resource.mod_controleponto.importacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.timmer.ControleTempoDeExecucao;
import com.volkmer.godinho.severino.entity.mod_controleponto.ArquivoImportacao;
import com.volkmer.godinho.severino.entity.mod_controleponto.Importacao;
import com.volkmer.godinho.severino.entity.mod_controleponto.Ponto;
import com.volkmer.godinho.severino.entity.mod_geral.usuario.Usuario;
import com.volkmer.godinho.severino.resource.mod_controleponto.arquivoimportacao.ArquivoImportacaoResource;
import com.volkmer.godinho.severino.resource.mod_controleponto.importador.ConverteExcelEmObjetoPonto;
import com.volkmer.godinho.severino.resource.mod_controleponto.ponto.PontoStatus;

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

	@Override
	protected void incluirPre(Importacao model) throws Exception {
		
		if (model.getArquivoimportacao()!=null) {

			try (ArquivoImportacaoResource res = new ArquivoImportacaoResource()){
				ArquivoImportacao arqimp = model.getArquivoimportacao();
				res.incluir(arqimp);
			} catch (Exception e) {
				throw e;
			}
			
		}
		
	}
	
	@Override
	protected void alterarPre(Importacao model) throws Exception {
		
		if (model.getArquivoimportacao()!=null) {
			
			try (ArquivoImportacaoResource res = new ArquivoImportacaoResource()){
				ArquivoImportacao arqimp = model.getArquivoimportacao();
				res.alterar(arqimp);
			} catch (Exception e) {
				throw e;
			}
			
		}
		
	}
	
	public List<Importacao> listarImportacoes(String userToken) {
		
		TypedQuery<Importacao> queryImportacao = this.getEm().createQuery("select i from Importacao i order by id desc", Importacao.class);
		List<Importacao> lista = queryImportacao.getResultList();
		
		lista.forEach(i -> i.setArquivoimportacao(null));
		
		return lista;
		
	}
	
	public Importacao importar(String userToken, Importacao importacao) throws Exception {

		ArquivoImportacao arq = new ArquivoImportacao();
		arq.setAnexo(importacao.getArquivoimportacao().getAnexo());
		importacao.setArquivoimportacao(arq);
		
		//Classe que controla o tempo que leva pra executar a importacão
		ControleTempoDeExecucao cte = new ControleTempoDeExecucao();
		//Informa Início
		cte.inicio();
		
		//Classe que controla o tempo que leva pra converter excel em objetos
		ControleTempoDeExecucao ctp = new ControleTempoDeExecucao();
		//Informa Início
		ctp.inicio();
		
		String nomecompletoarquivo = importacao.getNome();
		
		importacao.setNome(nomecompletoarquivo.substring(0, nomecompletoarquivo.lastIndexOf(".")));
		importacao.setExtensao(nomecompletoarquivo.substring(nomecompletoarquivo.lastIndexOf("."), nomecompletoarquivo.length()));
		importacao.setData_hora_importacao(LocalDateTime.now());
		//Antes de gravar seta a situação Pendente na importação
		importacao.setStatus(ImportacaoStatus.PENDENTE);
		//Seta tamanho do arquivo
		importacao.setTamanho(importacao.getArquivoimportacao().getAnexo().length);
		//Inclui arquivo no banco de dados
		this.incluir(importacao);
		this.commit();
		
		List<Ponto> listaObjPonto =  new ArrayList<>();
		
		try {
			listaObjPonto = new ConverteExcelEmObjetoPonto().importacao(importacao);
			importacao.setTempo_processamento(ctp.fim());
			try {
				new ProcessaPonto().processar(listaObjPonto, importacao);
			} catch (Exception e) {
				
				e.printStackTrace();
				importacao.setTempo_importacao(cte.fim());
				importacao.setStatus(ImportacaoStatus.IMPORTACAO_PARCIAL);
				
				this.alterar(importacao);
				this.commit();
				throw e;
				
			}
		} catch (Exception e) {
			
			importacao.setStatus(ImportacaoStatus.ERRO);
			importacao.setTempo_importacao(cte.fim());
			this.alterar(importacao);
			this.commit();
			
			throw e;
		}
		
		this.calcularEstatisticasDoArquivo(importacao);	
		this.removeArquivosSemVinculoComPonto();
		
		//Grava no banco o tempo que demorou para importar o arquivo
		importacao.setTempo_importacao(cte.fim());
		this.alterar(importacao);
		this.commit();
		
		return importacao;
		
	}

	private void removeArquivosSemVinculoComPonto() throws Exception {
		
		//Processo que seta como status sem vinculo arquivos que não estão mais vinculo com pontO
		TypedQuery<Importacao> queryImportacao = this.getEm().createQuery("select distinct i from Importacao i where i.id not in (select distinct importacao from Ponto)", Importacao.class);
		List<Importacao> listaImportacao = queryImportacao.getResultList();
		
		for (Importacao importacao : listaImportacao) {
			this.remover(importacao);
		}
		
	}

	private void calcularEstatisticasDoArquivo(Importacao importacaoGravada) {
		
		importacaoGravada.setData_hora_importacao(LocalDateTime.now());
		importacaoGravada.setUsuario_com_credito_banco(this.contaUsuarios(importacaoGravada,PontoStatus.CREDITO));
		importacaoGravada.setUsuario_com_debito_banco(this.contaUsuarios(importacaoGravada,PontoStatus.DEBITO));
		importacaoGravada.setUsuario_com_marcacao_incorreta(this.contaUsuarios(importacaoGravada,PontoStatus.MARCACAO_INCORRETA));
		importacaoGravada.setStatus(ImportacaoStatus.CONCLUIDO);
		
		//Usuários Sem Pendência
		Integer valor = importacaoGravada.getUsuario_com_marcacao_incorreta();
		
		if (valor<importacaoGravada.getUsuario_com_credito_banco()) {
			valor = importacaoGravada.getUsuario_com_credito_banco();
		}
		if (valor<importacaoGravada.getUsuario_com_debito_banco()) {
			valor = importacaoGravada.getUsuario_com_debito_banco();
		}
		
		importacaoGravada.setUsuario_sem_pendencias(importacaoGravada.getQuantidade_usuario()-valor);
		
	}

	private Integer contaUsuarios(Importacao importacao, PontoStatus pontoStatus) {
		TypedQuery<Usuario> queryPonto = this.getEm().createQuery("select u from Usuario u where u.id in (select p.usuario from Ponto p where p.importacao = :importacao and (:status is null or status = :status))", Usuario.class);
		queryPonto.setParameter("importacao", importacao);
		queryPonto.setParameter("status", pontoStatus);
		List<Usuario> lista = queryPonto.getResultList();
		return lista.size();
	}

	public List<Usuario> listarUsuarios(String userToken, Long id_importacao, PontoStatus status) {
		
		Importacao importacao = this.getEm().find(this.getModelClass(), id_importacao);
					
		TypedQuery<Usuario> queryPonto = this.getEm().createQuery("select u from Usuario u where u.id in (select p.usuario from Ponto p where p.importacao = :importacao and (:status is null or status = :status))", Usuario.class);
		queryPonto.setParameter("importacao", importacao);
		queryPonto.setParameter("status", status);

		List<Usuario> lista = queryPonto.getResultList();
		
		return lista;

	}
	
}