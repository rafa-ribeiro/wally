package com.warren.wally.model.investimento;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.repository.DividendoEntity;
import com.warren.wally.repository.DividendoRepository;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;

@Component
public class ProdutoFIIActor {

	@Resource
	private MovimentacaoRepository movimentacaoRepository;
	
	@Resource 
	private DividendoRepository dividendoRepository;
	
	@Autowired
	private DataMarketEquities dm;

	// TODO filtrar por tipo de movimentacao
	public ProdutoFIIVO run(LocalDate dataPosicao, String codigo) {
		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByCodigoAndDataLessThan(codigo,
				dataPosicao);
		ProdutoFIIVO produto = new ProdutoFIIVO(codigo);
		movimentacoes.stream().forEach(it -> adicionaMovimentacao(it, produto));

		return produto;
	}

	public List<ProdutoFIIVO> run(LocalDate dataPosicao) {
		
		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByDataLessThan(dataPosicao);

		Map<String, ProdutoFIIVO> produtos = new HashMap<>();

		for (MovimentacaoEntity entity : movimentacoes) {
			if (produtos.containsKey(entity.getCodigo())) {
				adicionaMovimentacao(entity, produtos.get(entity.getCodigo()));
			} else {
				ProdutoFIIVO vo = new ProdutoFIIVO(entity.getCodigo());
				vo.setCotacao(dm.get(entity.getCodigo(), dataPosicao));
				adicionaMovimentacao(entity, vo);
				produtos.put(entity.getCodigo(), vo);
			}
		}
		
		produtos.values().stream().forEach(produto -> {
			List<DividendoEntity> dividendos = dividendoRepository
					.findByCodigoAndDataLessThanOrderByDataDesc(produto.getCodigo(), dataPosicao);
			if(dividendos.size() > 0) {
				produto.setRentabilidadeDividendo(dividendos.get(0).getValorUnitario()/produto.getPrecoMedio());
			}
			
		});
		return produtos.values().stream().collect(Collectors.toList());
	}

	
	private void adicionaMovimentacao(MovimentacaoEntity mov, ProdutoFIIVO vo) {
		vo.setPrecoTotal(vo.getPrecoTotal() + mov.getValorUnitario() * mov.getQuantidade());
		vo.setQuantidade(vo.getQuantidade() + mov.getQuantidade());
	}
} 
