package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.investimento.DataMarketEquities;

import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDate;


public class TestYahooFinance extends WallyTestCase {

    @Resource
    private DataMarketEquities dm;

    @Test
    public void test() {

        double valor = dm.get("VISC11", LocalDate.of(2019, 01, 02));
        System.out.println(valor);
		/*
		Stock stock;
		try {
			stock = YahooFinance.get("VRTA11.SA", true);
			System.out.println("Lendooooo");
			BigDecimal price = stock.getQuote().getPrice();
			BigDecimal change = stock.getQuote().getChangeInPercent();
			BigDecimal peg = stock.getStats().getPeg();
			BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

			System.out.println(price);
			System.out.println(dividend);
			System.out.println("uhulll");
			stock.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

    }

}
