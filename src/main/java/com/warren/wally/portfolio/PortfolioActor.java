package com.warren.wally.portfolio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.ProdutoFIIActor;
import com.warren.wally.model.investimento.ProdutoFIIVO;
import com.warren.wally.model.investimento.ProdutoVO;
import com.warren.wally.repository.ProdutoRepository;

@Component
public class PortfolioActor {

	@Resource
	private ProdutoFIIActor actorFII;

	@Autowired
	private ProdutoRepository repository;

	@Resource
	private CalculadoraResolver calculadoraResolver;

	@Resource
	private InvestimentoResolver investimentoResolver;

	public PortfolioVO run(LocalDate dataRef) {
		List<ProdutoVO> produtosRF = getProdutosRF(dataRef);
		List<ProdutoFIIVO> produtosRV = getProdutosRV(dataRef);

		return PortfolioVO.builder().dataRef(dataRef).produtosRF(produtosRF).produtosRV(produtosRV)
				.accrual(calcAccrual(produtosRF, produtosRV)).build();
	}

	public double calcAccrual(List<ProdutoVO> produtosRF, List<ProdutoFIIVO> produtosRV) {
		double accrual = 0.0;
		accrual += produtosRF.stream().mapToDouble(it -> it.getValorPresente()).sum();
		accrual += produtosRV.stream().mapToDouble(it -> it.getValorPresente()).sum();
		return accrual;
	}

	public List<ProdutoVO> getProdutosRF(LocalDate dataRef) {
		List<ProdutoVO> produtos = new ArrayList<>();

		repository.findAll().forEach(p -> {
			try {
				Investimento invest = investimentoResolver.resolve(p.getTipoInvestimento());
				produtos.add(invest.calc(dataRef, p));
			} catch (Exception e) {
				System.out.println("Erro no produto: " + p + ": " + e.getMessage());
			}
		});
		return produtos;
	}

	public List<ProdutoFIIVO> getProdutosRV(LocalDate dataRef) {
		return actorFII.run(dataRef);
	}

}
