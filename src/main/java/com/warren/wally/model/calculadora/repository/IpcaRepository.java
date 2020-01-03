package com.warren.wally.model.calculadora.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IpcaRepository extends CrudRepository<IpcaEntity, Long> {

	public List<IpcaEntity> findAllByOrderByDataDesc();

	public List<IpcaEntity> findFirstByOrderByDataDesc();

	public List<IpcaEntity> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

	public List<IpcaEntity> findByData(LocalDate data);
}