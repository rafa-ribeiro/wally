package com.warren.wally.grafico;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GraficoTransformador {

	private Cores cores = new Cores();

	public String transforma(Map<String, Double> dados) {
		return transforma(dados, false);
	}

	public String transforma(Map<String, Double> dados, boolean coresClaras) {
		GraficoDados graficoDados = new GraficoDados();

		dados.forEach((key, value) -> {
			graficoDados.addLegenda((String) key);
			graficoDados.addValor(Math.floor(value * 100) / 100.0);
		});

		if (coresClaras) {
			graficoDados.setCores(cores.getCoresBorda(dados.size()));
			graficoDados.setCoresSecundarias(cores.getCoresClaras(dados.size()));
		} else {
			graficoDados.setCores(cores.getCores(dados.size()));
			graficoDados.setCoresSecundarias(cores.getCoresHover(dados.size()));
		}

		try {
			String json = new ObjectMapper().writeValueAsString(graficoDados);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "";

	}
}
