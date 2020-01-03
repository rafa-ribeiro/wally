package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.investimento.ProdutoFIIActor;
import com.warren.wally.model.investimento.ProdutoFIIVO;
import com.warren.wally.repository.MovimentacaoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


public class TestaProdutoFII extends WallyTestCase {

	@Autowired
	private ProdutoFIIActor actor;

	@Resource
	private MovimentacaoRepository movimentacaoRepository;

	/*@Before
	public void inicializa() {
		movimentacaoRepository.deleteAll();
		MovimentacaoEntity mov1 = new MovimentacaoEntity(LocalDate.of(2018, 04, 04), "VRTA11", 105.0, 2);
		MovimentacaoEntity mov2 = new MovimentacaoEntity(LocalDate.of(2018, 07, 27), "VRTA11", 106.3, 2);
		movimentacaoRepository.save(mov1);
		movimentacaoRepository.save(mov2);
		
		MovimentacaoEntity mov3 = new MovimentacaoEntity(LocalDate.of(2018, 4, 4), "BCRI11", 102.36, 2);
		MovimentacaoEntity mov4 = new MovimentacaoEntity(LocalDate.of(2018, 8, 27), "BCRI11", 104.61, 2);
		MovimentacaoEntity mov5 = new MovimentacaoEntity(LocalDate.of(2018, 10, 25), "BCRI11", 106.0, 3);
		movimentacaoRepository.save(mov3);
		movimentacaoRepository.save(mov4);
		movimentacaoRepository.save(mov5);
	}*/

	@Test
	public void test() {

		LocalDate hoje = LocalDate.of(2018, 12, 10);

		ProdutoFIIVO produtoActual = actor.run(hoje, "VRTA11");

		assertEquals(105.65, produtoActual.getPrecoMedio(), 0.01);
		assertEquals(4, produtoActual.getQuantidade(), 0.01);
	}
	
	@Test
	public void testList() {

		LocalDate hoje = LocalDate.of(2018, 12, 10);

		List<ProdutoFIIVO> produtos = actor.run(hoje);
		Optional<ProdutoFIIVO> vo1 = produtos.stream().filter(it -> it.getCodigo().equals("BCRI11")).findFirst();
		
		assertEquals(104.56, vo1.get().getPrecoMedio(), 0.01);
		assertEquals(7, vo1.get().getQuantidade(), 0.01);

	}

}
