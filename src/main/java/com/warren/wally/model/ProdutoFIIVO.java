package com.warren.wally.model;

public class ProdutoFIIVO {

	public ProdutoFIIVO(String codigo) {
		super();
		this.codigo = codigo;
	}

	private String codigo;
	private double precoTotal = 0.0;
	private int quantidade = 0;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public double getPrecoTotal() {
		return precoTotal;
	}
	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public double getPrecoMedio() {
		return this.precoTotal/this.quantidade;
	}
	

}