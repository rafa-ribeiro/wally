package com.warren.wally.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataValor implements Comparable<DataValor>{

	private LocalDate data;
	private double valor;

	public DataValor(String data, double valor) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.data = LocalDate.parse(data, dtf);
		this.valor = valor;
	}
	
	public DataValor(LocalDate data, double valor) {
		this.data = data;
		this.valor = valor;
	}

	public DataValor() {
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "[data=" + data + ", valor=" + valor + "]";
	}

	@Override
	public int compareTo(DataValor o) {
		return this.getData().compareTo(o.getData());
	}

}
