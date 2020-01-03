package com.warren.wally.model.calculadora.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CdiRepository extends CrudRepository<CdiEntity, Long> {

	public List<CdiEntity> findAllByOrderByDataDesc();

	public List<CdiEntity> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

	public List<CdiEntity> findByData(LocalDate data);
}
