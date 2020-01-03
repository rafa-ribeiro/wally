package com.warren.wally.model.investimento;

import java.time.LocalDate;

import com.warren.wally.utils.Leao;

public abstract class InvestimentoAbstract implements Investimento {

	protected boolean validaData(LocalDate dataRef, LocalDate dtAplicacao, LocalDate dtVencimento) {
		if (dtAplicacao.isAfter(dataRef)) {
			throw new RuntimeException(String.format("Produto não aplicado ainda. Data de aplicação: %s - Data: %s",
					dtAplicacao, dataRef));
		}
		if (dtVencimento.isBefore(dataRef)) {
			throw new RuntimeException(String.format("Produto vencido. Data de vencimento: %s - Data: %s",
					dtVencimento, dataRef));
		}
		return true;
	}



	protected double getRentabilidadeLiquida(ProdutoVO vo) {
		return vo.getValorPresente() / vo.getValorAplicado() - 1;
	}

	protected double getTaxaAnualLiquida(ProdutoVO vo) {
		if (vo.getDu() == 0) {
			return 0;
		}
		try {
			return Math.pow(vo.getRentabilidadeLiquida() + 1, 252.0 / vo.getDu()) - 1;
		} catch (Exception exp) {
			return 0.0;
		}
	}

	protected double getTaxaMensalLiquida(ProdutoVO vo) {
		if (vo.getDu() == 0) {
			return 0;
		}
		try {
			return Math.pow(vo.getRentabilidadeLiquida() + 1, 21.0 / vo.getDu()) - 1;
		} catch (Exception exp) {
			return 0.0;
		}
	}
}
