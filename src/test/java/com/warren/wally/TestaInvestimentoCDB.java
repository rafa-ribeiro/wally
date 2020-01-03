package com.warren.wally;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.junit.Test;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.ProdutoVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.repository.ProdutoEntity;

public class TestaInvestimentoCDB extends WallyTestCase {

	@Resource
	private CalculadoraResolver calculadoraResolver;

	@Resource
	private InvestimentoResolver investimentoResolver;

	@Test
	public void testCalculoPre() {

		ProdutoEntity entity = new ProdutoEntity();
		entity.setDtAplicacao(dateOf("24/06/2016"));
		entity.setVencimento(dateOf("31/05/2021"));
		entity.setValorAplicado(10000);
		entity.setTaxa(0.1221);
		entity.setTipoInvestimento(TipoInvestimento.CDB);
		entity.setTipoRentabilidade(TipoRentabilidade.PRE);
		LocalDate hoje = dateOf("10/12/2018");

		Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
		ProdutoVO produto = invest.calc(hoje, entity);
		assertEquals(12764.67, produto.getValorPresente(), 0.01);
		assertEquals(27.65, produto.getRentabilidadeLiquida() * 100, 0.01);
		assertEquals(10.50, produto.getTaxaAnualLiquida() * 100, 0.01);
		assertEquals(0.84, produto.getTaxaMensalLiquida() * 100, 0.01);
	}

	@Test
	public void testCalculoCDI() {

		ProdutoEntity entity = new ProdutoEntity();
		entity.setDtAplicacao(dateOf("24/06/2016"));
		entity.setVencimento(dateOf("31/05/2021"));
		entity.setValorAplicado(10000);
		entity.setTaxa(1.22);
		entity.setTipoInvestimento(TipoInvestimento.CDB);
		entity.setTipoRentabilidade(TipoRentabilidade.CDI);
		LocalDate hoje = dateOf("10/12/2018");

		Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
		ProdutoVO produto = invest.calc(hoje, entity);
		assertEquals(12606.04, produto.getValorPresente(), 0.01);
		assertEquals(26.06, produto.getRentabilidadeLiquida() * 100, 0.01);
		assertEquals(9.94, produto.getTaxaAnualLiquida() * 100, 0.01);
		assertEquals(0.79, produto.getTaxaMensalLiquida() * 100, 0.01);
	}

	@Test
	public void testCalculoIPCA() {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setDtAplicacao(dateOf("23/10/2018"));
		entity.setVencimento(dateOf("15/12/2024"));
		entity.setValorAplicado(1000);
		entity.setTaxa(0.07);
		entity.setTipoInvestimento(TipoInvestimento.CDB);
		entity.setTipoRentabilidade(TipoRentabilidade.IPCA);
		LocalDate hoje = dateOf("10/12/2018");

		Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
		ProdutoVO produto = invest.calc(hoje, entity);

		assertEquals(1008.03, produto.getValorPresente(), 0.01);
		assertEquals(0.80, produto.getRentabilidadeLiquida() * 100, 0.01);
		assertEquals(6.50, produto.getTaxaAnualLiquida() * 100, 0.01);
		assertEquals(0.53, produto.getTaxaMensalLiquida() * 100, 0.01);
	}

	@Test
	public void testCalculoPreLCI() {

		ProdutoEntity entity = new ProdutoEntity();
		entity.setDtAplicacao(dateOf("24/06/2016"));
		entity.setVencimento(dateOf("31/05/2021"));
		entity.setValorAplicado(10000);
		entity.setTaxa(0.1221);
		entity.setTipoInvestimento(TipoInvestimento.LCI);
		entity.setTipoRentabilidade(TipoRentabilidade.PRE);
		LocalDate hoje = dateOf("10/12/2018");

		Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
		ProdutoVO produto = invest.calc(hoje, entity);
		assertEquals(13252.55, produto.getValorPresente(), 0.01);
		assertEquals(32.53, produto.getRentabilidadeLiquida() * 100, 0.01);
		assertEquals(12.21, produto.getTaxaAnualLiquida() * 100, 0.01);
		assertEquals(0.96, produto.getTaxaMensalLiquida() * 100, 0.01);
	}
}
