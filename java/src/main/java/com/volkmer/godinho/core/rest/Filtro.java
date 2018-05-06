package com.volkmer.godinho.core.rest;

public class Filtro {

	private String nome;
	private Object valor;
	
	public Filtro() {
	}
	
	public Filtro(String nome, Object valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Object getValor() {
		return valor;
	}
	public void setValor(Object valor) {
		this.valor = valor;
	}
	
}
