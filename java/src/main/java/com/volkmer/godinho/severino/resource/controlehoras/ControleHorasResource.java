package com.volkmer.godinho.severino.resource.controlehoras;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.volkmer.godinho.core.resource.ResourceCRUD;
import com.volkmer.godinho.core.rest.filters.RestException;
import com.volkmer.godinho.severino.entity.AnoMes;
import com.volkmer.godinho.severino.entity.ControleHoras;
import com.volkmer.godinho.severino.entity.Ponto;
import com.volkmer.godinho.severino.entity.Usuario;
import com.volkmer.godinho.severino.resource.acesso.AcessoTipo;
import com.volkmer.godinho.severino.resource.usuario.UsuarioResource;

public class ControleHorasResource extends ResourceCRUD<Ponto> {
	
	public ControleHorasResource() {
	}
	
	@Override
	public Class<Ponto> getModelClass() {
		return Ponto.class;
	}
	
	private RestException erroUsuarioNaoPertenceAoCoordenadorLogado = new RestException("Usuário Não pertence ao coordenador logado.");
	
	public ControleHoras listarControleHoras(Long usuarioid, String token, Integer ano, Integer mes) throws Exception {
	
		AnoMes anomes = null;
		
		TypedQuery<AnoMes> queryAnoMes = this.getEm().createQuery("select p from AnoMes p where mes = :mes and ano = :ano", AnoMes.class);
		queryAnoMes.setParameter("mes", mes);
		queryAnoMes.setParameter("ano", ano);
		
		try {
			anomes = queryAnoMes.getSingleResult();
			
			Usuario usuario = new Usuario();
			Usuario usuariodoid =  new Usuario();
			
			try (UsuarioResource usuRes = new UsuarioResource()) {
				
				usuario = usuRes.buscaUsuarioPeloToken(token);
				usuariodoid = usuRes.buscaUsuarioPeloId(usuarioid);
				//Usuário encontrado agora retorna a lista de ponto do usuário
				TypedQuery<Ponto> queryPonto = this.getEm().createQuery("select p from Ponto p where p.usuario = :usuario and Month(p.data) = :mes and Year(p.data) = :ano order by p.data asc", Ponto.class);
				
				if (usuario.getAcesso().getTipo().equals(AcessoTipo.NORMAL)) {
					queryPonto.setParameter("usuario", usuario);
				} else if (usuario.getAcesso().getTipo().equals(AcessoTipo.COORDENADOR)) {
					if (usuariodoid.getDepartamento().getId().equals(usuario.getDepartamento().getId())) {
						queryPonto.setParameter("usuario", usuariodoid);
					} else {
						throw erroUsuarioNaoPertenceAoCoordenadorLogado;
					}
				}
				
				queryPonto.setParameter("mes", mes);
				queryPonto.setParameter("ano", ano);
				List<Ponto> lista = queryPonto.getResultList();
					
				lista.forEach(p -> p.getImportacao().setArquivoimportacao(null));
				
				Integer minutosCredito = 0;
				Integer minutosDebito = 0;
				Integer minutosAbono = 0;
				Integer minutosTrabalhados = 0;
				
				for (Ponto ponto : lista) {
					if (ponto.getMinutos_credito()!=null) {
						minutosCredito += ponto.getMinutos_credito();
					}
					if (ponto.getMinutos_debito()!=null) {
						minutosDebito += ponto.getMinutos_debito();
					} 
					if (ponto.getMinutos_abono()!=null) {
						minutosAbono += ponto.getMinutos_abono();
					} 
					if (ponto.getMinutos_trabalhados()!=null) {
						minutosTrabalhados += ponto.getMinutos_trabalhados();
					}
				}
				
				ControleHoras ch = new ControleHoras();
				ch.setAnomes(anomes);
				ch.setCredito(minutosCredito);
				ch.setDebito(minutosDebito);
				ch.setAbono(minutosAbono);
				ch.setTrabalhadas(minutosTrabalhados);
				
				Integer salm = 0;
				
				if (minutosDebito<=minutosCredito) {
					salm = (minutosCredito-minutosDebito);
				} else {
					salm = -(minutosDebito-minutosCredito);
				}
				
				ch.setSaldo(salm);
				ch.setAbsenteismo(this.calculaAbsenteismoMes(minutosTrabalhados,minutosDebito,minutosCredito,minutosAbono));
				
				return ch;
			
			} catch (NoResultException e) {
			}
			
		} catch (NoResultException e) {
		}
		
		return null;
		
	}

	private BigDecimal calculaAbsenteismoMes(Integer minTra, Integer minDeb, Integer minCre, Integer minAbono) {
		
		Integer neg = minDeb+minAbono;
		Integer pos = minCre+minTra;
		
		if (neg==0) {
			return BigDecimal.valueOf(0);
		} else {
			
			BigDecimal um = BigDecimal.valueOf(neg);
			BigDecimal dois = BigDecimal.valueOf(pos);
			if (um!=BigDecimal.valueOf(0) && dois!=BigDecimal.valueOf(0)) {
				BigDecimal tres = um.divide(dois, 2, RoundingMode.HALF_EVEN);
				return tres;
			} else {
				return BigDecimal.valueOf(0);
			}
			
		}		
		
	}

	public List<ControleHoras> listarControleHorasAno(Long usuarioid, String token, Integer ano) throws Exception {
		
		List<ControleHoras> listaControleHoras = new ArrayList<ControleHoras>();
		
		TypedQuery<AnoMes> queryAnoMes = this.getEm().createQuery("select p from AnoMes p where ano = :ano", AnoMes.class);
		queryAnoMes.setParameter("ano", ano);
		
		List<AnoMes> listaAnoMes = new ArrayList<>();
		
		try {
			listaAnoMes = queryAnoMes.getResultList();
			
			for (AnoMes anoMes : listaAnoMes) {
				ControleHoras contHor = this.listarControleHoras(usuarioid, token, anoMes.getAno(), anoMes.getMes());
				
				if (contHor!=null) {
					listaControleHoras.add(contHor);
				}
			}
			
		} catch (NoResultException e) {
		}
		
		return listaControleHoras;
		
	}

}