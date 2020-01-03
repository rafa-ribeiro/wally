package com.warren.wally.model.calculadora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.model.calculadora.repository.CdiRepository;
import com.warren.wally.utils.BussinessDaysCalendar;
import com.warren.wally.utils.DataValor;

@Component
public class CalculadoraCDIStrategy implements Calculadora {

	@Resource
	private CdiRepository cdiRepository;

	@Resource
	private BussinessDaysCalendar bc;

	private List<DataValor> cdi = new ArrayList<>();

	private LocalDate lastUpdatedDate;

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.CDI;
	}

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		return valorAplicado * getFatorAcumulado(taxa, filtraCDI(dtAplicacao, dtRef));
	}

	private double getFatorAcumulado(double taxa, List<DataValor> cdiFiltrado) {
		double fatorAcumulado = 1.0;
		for (DataValor dt : cdiFiltrado) {
			fatorAcumulado *= Math.pow(1 + dt.getValor() / 100 * taxa, 1 / 252.0);
		}
		return fatorAcumulado;
	}

	private List<DataValor> filtraCDI(LocalDate dataInicio, LocalDate dataFim) {

		List<DataValor> cdiFiltrado = getCdis().stream()
				.filter(dt -> dt.getData().isAfter(dataInicio)
						&& (dt.getData().isBefore(dataFim) || dt.getData().isEqual(dataFim)))
				.collect(Collectors.toList());

		return cdiFiltrado;

	}

	private List<DataValor> getCdis() {
		if (cdi.isEmpty() || !LocalDate.now().isEqual(lastUpdatedDate)) {
			lastUpdatedDate = LocalDate.now();
			cdi.clear();
			cdiRepository.findAll().forEach(it -> cdi.add(new DataValor(it.getData(), it.getValor())));
			completaDados(cdi, LocalDate.now());
		}
		return cdi;
	}

	private void completaDados(List<DataValor> cdi, LocalDate dataFim) {
		LocalDate today = bc.getNextWorkDay(dataFim);
		LocalDate lastDate = cdi.get(cdi.size() - 1).getData();
		double lastValue = cdi.get(cdi.size() - 1).getValor();
		while (lastDate.isBefore(today)) {
			lastDate = bc.getNextWorkDay(lastDate.plusDays(1));
			cdi.add(new DataValor(lastDate, lastValue));
		}
	}
}