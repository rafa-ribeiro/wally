package com.warren.wally.model.investimento;

import org.springframework.stereotype.Component;

@Component
public class InvestimentoDebenture extends InvestimentoCDB {

	@Override
	public TipoInvestimento getTipoInvestimento() {
		return TipoInvestimento.DEBENTURE;
	}
}
