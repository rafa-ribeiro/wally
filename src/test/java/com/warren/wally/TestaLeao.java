package com.warren.wally;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.utils.Leao;

public class TestaLeao extends WallyTestCase {

	@Resource
	private Leao leao;
	
	@Test
	public void test() {
		assertEquals(22.5, leao.getIR(100, dateOf("01/01/2018"), dateOf("01/02/2018")), 0.01);
		assertEquals(20.0, leao.getIR(100, dateOf("01/01/2018"), dateOf("01/07/2018")), 0.01);
		assertEquals(17.5, leao.getIR(100, dateOf("01/01/2018"), dateOf("01/02/2019")), 0.01);
		assertEquals(15.0, leao.getIR(100, dateOf("01/01/2018"), dateOf("01/01/2020")), 0.01);
	}

}
