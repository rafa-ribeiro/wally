package com.warren.wally.utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FeriadoRepository extends CrudRepository<FeriadoEntity, Long> {

	List<FeriadoEntity> findByData(LocalDate data);
}
