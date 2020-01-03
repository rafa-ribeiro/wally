package com.warren.wally.model.investimento;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.utils.BussinessDaysCalendar;

@Component
public class InvestimentoLCI extends InvestimentoAbstract {

	@Resource
	private CalculadoraResolver calculadoraResolver;

	@Resource
	private BussinessDaysCalendar bc;

	@Override
	public TipoInvestimento getTipoInvestimento() {
		return TipoInvestimento.LCI;
	}

	@Override
	public ProdutoVO calc(LocalDate dataRef, ProdutoEntity entity) {

		validaData(dataRef, entity.getDtAplicacao(), entity.getVencimento());

		ProdutoVO vo = ProdutoVO.builder().corretora(entity.getCorretora()).dtAplicacao(entity.getDtAplicacao())
				.dtVencimento(entity.getVencimento()).instituicao(entity.getInstituicao()).taxa(entity.getTaxa())
				.valorAplicado(entity.getValorAplicado()).tipoInvestimento(entity.getTipoInvestimento())
				.tipoRentabilidade(entity.getTipoRentabilidade()).dataReferencia(dataRef)
				.du(bc.getDu(entity.getDtAplicacao(), dataRef)).build();

		Calculadora calc = calculadoraResolver.resolve(vo.getTipoRentabilidade());

		double VPBruto = calc.calculaVPBruto(vo.getValorAplicado(), vo.getTaxa(), vo.getDtAplicacao(),
				vo.getDataReferencia());
		vo.setValorPresente(getValorPresente(vo, VPBruto));
		vo.setRentabilidadeLiquida(getRentabilidadeLiquida(vo));
		vo.setTaxaAnualLiquida(getTaxaAnualLiquida(vo));
		vo.setTaxaMensalLiquida(getTaxaMensalLiquida(vo));

		return vo;
	}

	protected double getValorPresente(ProdutoVO vo, double VPBruto) {
		return VPBruto;
	}
}
