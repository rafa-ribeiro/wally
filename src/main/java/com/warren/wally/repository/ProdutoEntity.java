package com.warren.wally.repository;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "produto")
@Getter
@Setter
public class ProdutoEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	private String corretora;
	private String instituicao;
	
	@Enumerated(EnumType.STRING)
	private TipoInvestimento tipoInvestimento;
	
	@Enumerated(EnumType.STRING)
	private TipoRentabilidade tipoRentabilidade;
	
	private LocalDate dtAplicacao;
	private double valorAplicado;
	private double taxa;
	private LocalDate vencimento;
	
	
	@Override
	public String toString() {
		return String.format("%s %s %s %.2f %.2f %s ", tipoInvestimento, tipoRentabilidade, instituicao, taxa,
				valorAplicado, dtAplicacao);
	}
	
	
}


