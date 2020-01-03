package com.warren.wally.utils;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "feriado")
public class FeriadoEntity {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDate data;
}