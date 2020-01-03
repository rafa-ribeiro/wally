package com.warren.wally.model.investimento;

import org.springframework.stereotype.Component;

@Component
public class InvestimentoTESOURO extends InvestimentoCDB {

	@Override
	public TipoInvestimento getTipoInvestimento() {
		return TipoInvestimento.TESOURO;
	}
}
