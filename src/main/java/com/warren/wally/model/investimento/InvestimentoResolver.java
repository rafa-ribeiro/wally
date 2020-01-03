package com.warren.wally.model.investimento;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class InvestimentoResolver {

	@Resource
	private InvestimentoCDB investimentoCDB;

	@Resource
	private InvestimentoLCI investimentoLCI;

	@Resource
	private InvestimentoDebenture investimentoDebenture;

	@Resource
	private InvestimentoLC investimentoLC;

	@Resource
	private InvestimentoTESOURO investimentoTESOURO;

	public Investimento resolve(TipoInvestimento tipoInvestimento) {
		if (tipoInvestimento.equals(TipoInvestimento.CDB)) {
			return investimentoCDB;
		}
		if (tipoInvestimento.equals(TipoInvestimento.LCI)) {
			return investimentoLCI;
		}
		if (tipoInvestimento.equals(TipoInvestimento.DEBENTURE)) {
			return investimentoDebenture;
		}
		if (tipoInvestimento.equals(TipoInvestimento.LC)) {
			return investimentoLC;
		}
		if (tipoInvestimento.equals(TipoInvestimento.TESOURO)) {
			return investimentoTESOURO;
		}
		throw new RuntimeException("Tipo de investimento " + tipoInvestimento + " não existe.");
	}

}
