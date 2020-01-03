package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.utils.BussinessDaysCalendar;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import javax.annotation.Resource;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

public class TestBussinessCalendarDays extends WallyTestCase {

    private LocalDate start;
    private LocalDate end;
    
    @Resource
    private BussinessDaysCalendar bc;

    @Before
    public void inicializa() {
        start = dateOf("01/12/2017");
        end = dateOf("07/11/2022");
    }

    @Test
    public void testDC() {
        assertEquals(1802, bc.getDc(start, end));
    }

    @Test
    public void testDU() {
        assertEquals(1237, bc.getDu(start, end));
    }
    
    @Test
    public void testNextWorkDay() {
    	assertEquals(dateOf("27/06/2016"), bc.getNextWorkDay(dateOf("27/06/2016")));
    	assertEquals(dateOf("01/03/2017"), bc.getNextWorkDay(dateOf("25/02/2017")));
    }

}
