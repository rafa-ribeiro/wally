package com.warren.wally.utils;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class Leao {

	private double getAliquota(LocalDate start, LocalDate end) {
		BussinessDaysCalendar bc = new BussinessDaysCalendar();
		long prazo = bc.getDc(start, end);
		if (prazo <= 180) {
			return 22.5 / 100;
		}
		if (prazo <= 360) {
			return 20.0 / 100;
		}
		if (prazo <= 720) {
			return 17.5 / 100;
		} else
			return 15.0 / 100;
	}

	public double getIR(double rentabilidadeBruta, LocalDate start, LocalDate end) {
		double aliquota = this.getAliquota(start, end);
		return rentabilidadeBruta * aliquota;
	}
}
