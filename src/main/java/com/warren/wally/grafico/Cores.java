package com.warren.wally.grafico;

import java.util.ArrayList;
import java.util.List;

public class Cores {

	List<String> cores;
	List<String> coresHover;
	List<String> coresClaras;
	List<String> coresBorda;

	public Cores() {
		cores = new ArrayList<>();
		cores.add("#F7464A");
		cores.add("#46BFBD");
		cores.add("#FDB45C");
		cores.add("#949FB1");
		cores.add("#4D5360");
		cores.add("#FF00BF");
		cores.add("#3366ff");
		cores.add("#47d147");
		cores.add("#F7464A");
		cores.add("#46BFBD");
		cores.add("#FDB45C");
		cores.add("#949FB1");
		cores.add("#4D5360");

		coresHover = new ArrayList<>();
		coresHover.add("#FF5A5E");
		coresHover.add("#5AD3D1");
		coresHover.add("#FFC870");
		coresHover.add("#A8B3C5");
		coresHover.add("#616774");
		coresHover.add("#ff4dd2");
		coresHover.add("#668cff");
		coresHover.add("#70db70");
		coresHover.add("#FF5A5E");
		coresHover.add("#5AD3D1");
		coresHover.add("#FFC870");
		coresHover.add("#A8B3C5");
		coresHover.add("#616774");

		coresClaras = new ArrayList<>();
		coresClaras.add("rgba(255, 99, 132, 0.2)");
		coresClaras.add("rgba(255, 159, 64, 0.2)");
		coresClaras.add("rgba(255, 205, 86, 0.2)");
		coresClaras.add("rgba(75, 192, 192, 0.2)");
		coresClaras.add("rgba(54, 162, 235, 0.2)");
		coresClaras.add("rgba(153, 102, 255, 0.2)");
		coresClaras.add("rgba(201, 203, 207, 0.2)");
		coresClaras.add("rgb(255, 77, 210, 0.2)");
		coresClaras.add("rgb(128, 255, 128, 0.2)");
		coresClaras.add("rgb(255, 153, 102, 0.2)");
		coresClaras.add("rgb(255, 204, 204, 0.2)");
		coresClaras.add("rgb(204, 204, 179, 0.2)");
		coresClaras.add("rgb(102, 255, 179, 0.2)");
		coresClaras.add("rgba(255, 99, 132, 0.2)");
		coresClaras.add("rgba(255, 159, 64, 0.2)");
		coresClaras.add("rgba(255, 205, 86, 0.2)");
		coresClaras.add("rgba(75, 192, 192, 0.2)");
		coresClaras.add("rgba(54, 162, 235, 0.2)");
		coresClaras.add("rgba(153, 102, 255, 0.2)");

		coresBorda = new ArrayList<>();
		coresBorda.add("rgb(255, 99, 132)");
		coresBorda.add("rgb(255, 159, 64)");
		coresBorda.add("rgb(255, 205, 86)");
		coresBorda.add("rgb(75, 192, 192)");
		coresBorda.add("rgb(54, 162, 235)");
		coresBorda.add("rgb(153, 102, 255)");
		coresBorda.add("rgb(201, 203, 207)");
		coresBorda.add("rgb(255, 77, 210)");
		coresBorda.add("rgb(128, 255, 128)");
		coresBorda.add("rgb(255, 153, 102)");
		coresBorda.add("rgb(255, 204, 204)");
		coresBorda.add("rgb(204, 204, 179)");
		coresBorda.add("rgb(102, 255, 179)");
		coresBorda.add("rgb(255, 99, 132)");
		coresBorda.add("rgb(255, 159, 64)");
		coresBorda.add("rgb(255, 205, 86)");
		coresBorda.add("rgb(75, 192, 192)");
		coresBorda.add("rgb(54, 162, 235)");
		coresBorda.add("rgb(153, 102, 255)");

	}

	public List<String> getCores(int tamanho) {
		if (tamanho > cores.size()) {
			throw new RuntimeException("Adicone cores na lista de cores");
		}
		return cores.subList(0, tamanho);
	}

	public List<String> getCoresHover(int tamanho) {
		if (tamanho > coresHover.size()) {
			throw new RuntimeException("Adicone cores na lista de coresHover");
		}
		return coresHover.subList(0, tamanho);
	}

	public List<String> getCoresClaras(int tamanho) {
		if (tamanho > coresClaras.size()) {
			throw new RuntimeException("Adicone cores na lista de coresClaras");
		}
		return coresClaras.subList(0, tamanho);
	}

	public List<String> getCoresBorda(int tamanho) {
		if (tamanho > coresBorda.size()) {
			throw new RuntimeException("Adicone " + (tamanho - coresBorda.size()) + " cores na lista de coresBorda");
		}
		return coresBorda.subList(0, tamanho);
	}
}
