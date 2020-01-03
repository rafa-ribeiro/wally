package com.warren.wally.model.investimento;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.repository.BolsaEntity;
import com.warren.wally.repository.BolsaRepository;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

@Component
public class DataMarketEquities {
	
	@Resource
	private BolsaRepository bolsaRepository;

	//TODO salvar no banco de dados, buscar sempre do yahoo é muito lento
	public double get(String ticker, LocalDate dataRef) {

		//busca no banco ticker
		List<BolsaEntity> entity = bolsaRepository.findFirstByCodigoOrderByDataDesc(ticker);
		
		//se nao achar busca historico de 3 anos e add na base
		if(entity == null || entity.size() == 0) {
			Calendar startDate = Calendar.getInstance();
			startDate.add(Calendar.YEAR, -2);
			Calendar endDate = Calendar.getInstance();
			searchForTicker(ticker, startDate, endDate);
			
			entity = bolsaRepository.findFirstByCodigoOrderByDataDesc(ticker);
			if(entity == null || entity.size() == 0) {
				System.out.println("Ticker não encontrado: " + ticker);
				return 0.0;
			}
		}
		//busca data especifica 
		List<BolsaEntity> bolsaValor = bolsaRepository.findByCodigoAndData(ticker, dataRef);
		//se não achar busca historico hoje - ultima data
		if(bolsaValor == null || bolsaValor.size() == 0) {
			LocalDate ultimaData = entity.get(0).getData();
			Calendar startDate = Calendar.getInstance();
			startDate.clear();
			startDate.set(ultimaData.getYear(), ultimaData.getMonthValue() - 1, ultimaData.getDayOfMonth());
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			Calendar endDate = Calendar.getInstance();
			searchForTicker(ticker, startDate, endDate);
			bolsaValor = bolsaRepository.findByCodigoAndData(ticker, dataRef);
			if(bolsaValor == null || bolsaValor.size() == 0) {
				System.out.println("Ticker não encontrado para a data: " + ticker + " " + dataRef);
				return 0.0;
			}
		}
		
		return bolsaValor.get(0).getValor();
	}
	
	private void searchForTicker(String ticker, Calendar startDate, Calendar endDate) {
		try {
			Stock stock = YahooFinance.get(ticker + ".SA");
			List<HistoricalQuote> histQuotes = stock.getHistory(startDate, endDate, Interval.DAILY);
			for (HistoricalQuote quote : histQuotes) {
				if(quote.getDate() != null && quote.getClose() != null) {
					LocalDate data = LocalDateTime.ofInstant(quote.getDate().toInstant(), ZoneId.systemDefault())
							.toLocalDate();
					BolsaEntity bolsa = new BolsaEntity();
					bolsa.setCodigo(ticker);
					bolsa.setData(data);
					bolsa.setValor(quote.getClose().doubleValue());
					bolsaRepository.save(bolsa);	
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
