package com.warren.wally.model.calculadora.repository;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "cdi")
@Getter
@Setter
public class CdiEntity {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDate data;

	private double valor;

}