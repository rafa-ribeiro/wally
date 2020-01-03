package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

public class TestaProdutoRepository extends WallyTestCase {

    @Autowired
    ProdutoRepository repository;

    @Test
    public void testaAdicionaProduto() {
        repository.deleteAll();
        ProdutoEntity produto = new ProdutoEntity();
        produto.setCorretora("EASYNVEST");
        produto.setInstituicao("FIBRA");
        produto.setValorAplicado(6000.12);
        produto.setTaxa(0.1221);
        produto.setTipoInvestimento(TipoInvestimento.CDB);
        produto.setTipoRentabilidade(TipoRentabilidade.PRE);
        produto.setDtAplicacao(dateOf("01/12/2017"));
        produto.setVencimento(dateOf("01/12/2018"));
        repository.save(produto);

        List<ProdutoEntity> produtosFromDB = repository.findByTipoInvestimento(TipoInvestimento.CDB);
        ProdutoEntity produtoFromDB = produtosFromDB.get(0);
        assertEquals(produto.getCorretora(), produtoFromDB.getCorretora());
        assertEquals(produto.getDtAplicacao(), produtoFromDB.getDtAplicacao());
        assertEquals(produto.getTipoInvestimento(), produtoFromDB.getTipoInvestimento());
    }

}
