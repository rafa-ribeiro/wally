package com.warren.wally.model.calculadora;

import java.time.LocalDate;

public interface Calculadora {

	TipoRentabilidade getTipoRentabilidade();
	
	double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef);
	
}
