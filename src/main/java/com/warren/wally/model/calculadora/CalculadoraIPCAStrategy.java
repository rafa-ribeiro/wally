package com.warren.wally.model.calculadora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.model.calculadora.repository.IpcaEntity;
import com.warren.wally.model.calculadora.repository.IpcaRepository;
import com.warren.wally.utils.BussinessDaysCalendar;
import com.warren.wally.utils.DataValor;

@Component
public class CalculadoraIPCAStrategy implements Calculadora {

	@Resource
	private BussinessDaysCalendar bc;

	@Resource
	private IpcaRepository ipcaRepository;

	private List<DataValor> ipca = new ArrayList<>();

	private LocalDate lastUpdatedDate;

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.IPCA;
	}

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		double fatorPre = getFatorPre(dtAplicacao, dtRef, taxa);
		double ipcaAcum = getFatorAcumulado(dtAplicacao, dtRef);
		return valorAplicado * fatorPre * ipcaAcum;
	}

	private double getFatorPre(LocalDate dtInicio, LocalDate dtFim, double taxa) {
		long du = bc.getDu(dtInicio, dtFim);
		if (du < 0)
			System.out.println("du é menor que zero");
		return Math.pow(1 + taxa, du / 252.0);
	}

	private double getFatorAcumulado(LocalDate dtInicio, LocalDate dtFim) {
		if (dtInicio.isAfter(dtFim))
			System.out.println("Data início é maior que data fim");

		LocalDate mesInicio = dtInicio.withDayOfMonth(1);
		LocalDate mesInicioMais1 = mesInicio.plusMonths(1);
		LocalDate mesFim = dtFim.withDayOfMonth(1);
		LocalDate mesFimMais1 = mesFim.plusMonths(1);

		if (mesInicio.isEqual(mesFim)) {
			long du = bc.getDu(dtInicio, dtFim);
			return Math.pow(find(mesInicioMais1) / find(mesInicio), du / 21.0);
		}
		long duInicio = bc.getDu(dtInicio, mesInicioMais1);
		long duFim = bc.getDu(mesFim, dtFim);
		double fteInicio = Math.pow(find(mesInicioMais1) / find(mesInicio), duInicio / 21.0);
		double fteMeio = find(mesFim) / find(mesInicioMais1);
		double fteFim = Math.pow(find(mesFimMais1) / find(mesFim), duFim / 21.0);
		return fteInicio * fteMeio * fteFim;

	}

	private double find(LocalDate dataRef) {
		Optional<DataValor> ipcaAcumuladoFiltrado = getIpcas().stream().filter(dt -> dt.getData().isEqual(dataRef))
				.findFirst();

		if (ipcaAcumuladoFiltrado.isPresent()) {
			return ipcaAcumuladoFiltrado.get().getValor();
		}
		throw new RuntimeException("Data de IPCA não tratada - " + dataRef);
	}

	private List<DataValor> getIpcas() {
		if (ipca.isEmpty() || !LocalDate.now().isEqual(lastUpdatedDate)) {
			lastUpdatedDate = LocalDate.now();
			ipca.clear();
			ipcaRepository.findAll().forEach(it -> {
				ipca.add(new DataValor(it.getData(), it.getValorAcum()));
			});
			completaDados(ipca, LocalDate.now());
		}
		return ipca;
	}

	private void completaDados(List<DataValor> ipca, LocalDate dataFim) {
		LocalDate nextMonth = dataFim.withDayOfMonth(1).plusMonths(1);
		List<IpcaEntity> lastIpca = ipcaRepository.findFirstByOrderByDataDesc();
		LocalDate lastDate = lastIpca.get(0).getData();
		double lastValue = lastIpca.get(0).getValor();
		double lastAcum = lastIpca.get(0).getValorAcum();
		while (lastDate.isBefore(nextMonth)) {
			lastDate = lastDate.plusMonths(1);
			lastAcum = lastAcum * (1 + lastValue / 100);
			ipca.add(new DataValor(lastDate, lastValue));
		}
	}

}
