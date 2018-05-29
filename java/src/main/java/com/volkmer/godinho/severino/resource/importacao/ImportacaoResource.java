package com.volkmer.godinho.severino.resource.importacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.timmer.ControleTempoDeExecucao;
import com.volkmer.godinho.severino.entity.Acesso;
import com.volkmer.godinho.severino.entity.ArquivoImportacao;
import com.volkmer.godinho.severino.entity.Importacao;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.arquivoimportacao.ArquivoImportacaoResource;
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
		
		if (this.ehUsarioAdmin(userToken)) {
			
			TypedQuery<Importacao> queryImportacao = this.getEm().createQuery("select i from Importacao i order by id desc", Importacao.class);
			List<Importacao> lista = queryImportacao.getResultList();
			
			lista.forEach(i -> i.setArquivoimportacao(null));
			
			return lista;
			
		}
		
		return null;
		
	}
	
	public Importacao gravar(String userToken, Importacao importacao) throws Exception {
		
		if (this.ehUsarioAdmin(userToken)) {
		
			//Codigo usado pra teste buscando arquivo fisico
			//Temporario para teste
			//String caminho = "D:\\repositorio\\severino\\excelponto\\Ponto.xls";
			//byte[] array = Files.readAllBytes(new File(caminho).toPath());
			//ArquivoImportacao arq = new ArquivoImportacao();
			//arq.setAnexo(array);

			ArquivoImportacao arq = new ArquivoImportacao();
			arq.setAnexo(importacao.getArquivoimportacao().getAnexo());
			importacao.setArquivoimportacao(arq);
			
			//Classe que controla o tempo que leva pra executar a importacão
			ControleTempoDeExecucao cte = new ControleTempoDeExecucao();
			//Informa Início
			cte.inicio();
			
			String nomecompletoarquivo = importacao.getNome();
			
			importacao.setNome(nomecompletoarquivo.substring(0, nomecompletoarquivo.lastIndexOf(".")));
			importacao.setExtensao(nomecompletoarquivo.substring(nomecompletoarquivo.lastIndexOf("."), nomecompletoarquivo.length()));
			
			//Antes de gravar seta a situação Pendente na importação
			importacao.setStatus(ImportacaoStatus.PENDENTE);
			//Seta tamanho do arquivo
			importacao.setTamanho(importacao.getArquivoimportacao().getAnexo().length);
			//Inclui arquivo no banco de dados
			this.incluir(importacao);
			this.commit();
						
			for (ObjetoPontoCompleto obj : this.retornaListaFinal(importacao)) {
				//Grava Registro do ponto e Vincula a Impotação
				try (ImportadorResource res = new ImportadorResource()) {
					res.gravarAlterarPonto(obj,userToken,importacao);
				} catch (Exception e) {
					throw e;
				}
				//Seta período que esta no arquivo
				if (importacao.getInicio_periodo()==null) {
					importacao.setInicio_periodo(obj.getData_inicial_importacao());
				}
				if (importacao.getFinal_periodo()==null) {
					importacao.setFinal_periodo(obj.getData_final_importacao());
				}
	
			}
			
			this.calcularEstatisticasDoArquivo(importacao);	
			
			//Grava no banco o tempo que demorou para importar o arquivo
			importacao.setTempo_importacao(cte.fim());
			this.alterar(importacao);
			this.commit();
			
		}
		
		return null;
		
	}
	
	private void calcularEstatisticasDoArquivo(Importacao importacaoGravada) {
		
		importacaoGravada.setData_hora_importacao(LocalDateTime.now());
		importacaoGravada.setQuantidade_usuario(this.contaUsuarios(importacaoGravada,null));
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

	public List<Usuario> listarUsuarios(String userToken, Long id_importacao, PontoStatus status) {
		
		if (this.ehUsarioAdmin(userToken)) {
			
			Importacao importacao = this.getEm().find(this.getModelClass(), id_importacao);
						
			TypedQuery<Usuario> queryPonto = this.getEm().createQuery("select u from Usuario u where u.id in (select p.usuario from Ponto p where p.importacao = :importacao and (:status is null or status = :status))", Usuario.class);
			queryPonto.setParameter("importacao", importacao);
			queryPonto.setParameter("status", status);
	
			List<Usuario> lista = queryPonto.getResultList();
			
			return lista;
			
		}
		
		return null;
	}
	
}
