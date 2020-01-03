package com.warren.wally.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.warren.wally.model.investimento.TipoInvestimento;


public interface ProdutoRepository extends CrudRepository<ProdutoEntity, Long>{

	List<ProdutoEntity> findByTipoInvestimento(TipoInvestimento tipoInvestimento);
}
