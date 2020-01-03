package com.warren.wally;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.portfolio.MultiPortfolio;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import com.warren.wally.portfolio.VariacaoVO;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

public class TestaPortfolio extends WallyTestCase {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PortfolioActor portfolioActor;
	
	@Autowired
	private MultiPortfolio multiPortfolio;

	private LocalDate dataRef;
	
	@Before
	public void inicializa() {
		dataRef = dateOf("10/12/2018");
		
		ProdutoEntity entityPre = new ProdutoEntity();
		entityPre.setDtAplicacao(dateOf("24/06/2016"));
		entityPre.setVencimento(dateOf("31/05/2021"));
		entityPre.setValorAplicado(10000);
		entityPre.setTaxa(0.1221);
		entityPre.setTipoInvestimento(TipoInvestimento.CDB);
		entityPre.setTipoRentabilidade(TipoRentabilidade.PRE);
		produtoRepository.save(entityPre);

		ProdutoEntity entityCdi = new ProdutoEntity();
		entityCdi.setDtAplicacao(dateOf("24/06/2016"));
		entityCdi.setVencimento(dateOf("31/05/2021"));
		entityCdi.setValorAplicado(10000);
		entityCdi.setTaxa(1.22);
		entityCdi.setTipoInvestimento(TipoInvestimento.CDB);
		entityCdi.setTipoRentabilidade(TipoRentabilidade.CDI);
		produtoRepository.save(entityCdi);
		
		ProdutoEntity entityIpca = new ProdutoEntity();
		entityIpca.setDtAplicacao(dateOf("23/10/2018"));
		entityIpca.setVencimento(dateOf("15/12/2024"));
		entityIpca.setValorAplicado(1000);
		entityIpca.setTaxa(0.07);
		entityIpca.setTipoInvestimento(TipoInvestimento.CDB);
		entityIpca.setTipoRentabilidade(TipoRentabilidade.IPCA);
		produtoRepository.save(entityIpca);
	}
	

	@After
	public void clear() {
		produtoRepository.deleteAll();
	}

	@Test
	public void testAccrual() {
		PortfolioVO portfolio = portfolioActor.run(dataRef);
		assertEquals(26378.74, portfolio.getAccrual(), 0.01);
	}
	
	@Test
	public void testMultiportfolio() {
		PortfolioVO portfolio = portfolioActor.run(dataRef);
		VariacaoVO variacao = multiPortfolio.calculaVariacoes(portfolio);
		assertEquals(3132.92, variacao.getAnualAbsoluto(), 0.01);
		assertEquals(174.39, variacao.getMensalAbsoluto(), 0.01);
	}

}
