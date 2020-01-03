package com.warren.wally.model.investimento;

import org.springframework.stereotype.Component;

@Component
public class InvestimentoLC extends InvestimentoCDB {

	@Override
	public TipoInvestimento getTipoInvestimento() {
		return TipoInvestimento.LC;
	}
}
