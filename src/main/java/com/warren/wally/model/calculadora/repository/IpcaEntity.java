package com.warren.wally.model.calculadora.repository;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "ipca")
@Getter
@Setter
public class IpcaEntity {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDate data;

	private double valor;

	private double valorAcum;

}
