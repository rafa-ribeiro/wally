package com.warren.wally.model.investimento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoFIIVO {

	public ProdutoFIIVO(String codigo) {
		super();
		this.codigo = codigo;
	}

	private String codigo;
	private double precoTotal = 0.0;
	private int quantidade = 0;
	private double cotacao = 0.0;
	private double rentabilidadeDividendo = 0.0;

	public double getPrecoMedio() {
		return this.precoTotal / this.quantidade;
	}

	public double getValorPresente() {
		return getQuantidade() * cotacao;
	}

	public double getResultado() {
		return getValorPresente() - getPrecoTotal();
	}
}
