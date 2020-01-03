package com.warren.wally.model.investimento;

import java.time.LocalDate;

import com.warren.wally.repository.ProdutoEntity;

public interface Investimento {

	TipoInvestimento getTipoInvestimento();

	ProdutoVO calc(LocalDate hoje, ProdutoEntity produto);

}
