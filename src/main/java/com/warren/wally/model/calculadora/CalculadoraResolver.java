package com.warren.wally.model.calculadora;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class CalculadoraResolver {
	
	@Resource
	private CalculadoraCDIStrategy calculadoraCdi;
	
	@Resource
	private CalculadoraPreStrategy calculadoraPre;
	
	@Resource
	private CalculadoraIPCAStrategy calculadoraIpca;
	
	public Calculadora resolve(TipoRentabilidade tipoRentabilidade) {
		if(tipoRentabilidade.equals(TipoRentabilidade.CDI)) {
			return calculadoraCdi;
		}
		if(tipoRentabilidade.equals(TipoRentabilidade.PRE)) {
			return calculadoraPre;
		}
		if(tipoRentabilidade.equals(TipoRentabilidade.IPCA)) {
			return calculadoraIpca;
		}
		throw new RuntimeException("Calculadora não tratada " + tipoRentabilidade);
	}

}
