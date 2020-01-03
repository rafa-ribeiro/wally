package com.warren.wally.grafico;

import java.util.ArrayList;
import java.util.List;

public class GraficoDados {

	public List<String> legendas;
	public List<Double> valores;
	public List<String> cores;
	public List<String> coresSecundarias;

	public List<String> getLegendas() {
		return legendas;
	}

	public void setLegendas(List<String> legendas) {
		this.legendas = legendas;
	}

	public List<Double> getValores() {
		return valores;
	}

	public void setValores(List<Double> valores) {
		this.valores = valores;
	}

	public List<String> getCores() {
		return cores;
	}

	public void setCores(List<String> cores) {
		this.cores = cores;
	}

	public List<String> getCoresSecundarias() {
		return coresSecundarias;
	}

	public void setCoresSecundarias(List<String> coresSecundarias) {
		this.coresSecundarias = coresSecundarias;
	}

	public void addLegenda(String legenda) {
		if (legendas == null) {
			legendas = new ArrayList<>();
		}
		legendas.add(legenda);
	}

	public void addValor(Double valor) {
		if (valores == null) {
			valores = new ArrayList<>();
		}
		valores.add(valor);
	}

}
