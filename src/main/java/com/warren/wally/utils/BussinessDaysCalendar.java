package com.warren.wally.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class BussinessDaysCalendar {

	@Resource
	private FeriadoRepository feriadoRepository;

	private List<LocalDate> holidays = new ArrayList<>(); 

	public long getDu(LocalDate start, LocalDate end) {
		long count = 0;
		if (start.isAfter(end)) {
			return -1;
		}
		if (start.isEqual(end)) {
			return 0;
		}

		LocalDate iterator = start.plusDays(1);
		while (iterator.isBefore(end) || iterator.isEqual(end)) {
			if (isWorkDay(iterator)) {
				count++;
			}
			iterator = iterator.plusDays(1);
		}
		return count;
	}

	public long getDc(LocalDate start, LocalDate end) {
		return ChronoUnit.DAYS.between(start, end);
	}

	public LocalDate getNextWorkDay(LocalDate date) {
		if (isWorkDay(date)) {
			return date;
		}
		LocalDate iterator = date.plusDays(1);
		while (!isWorkDay(iterator)) {
			iterator = iterator.plusDays(1);
		}
		return iterator;
	}

	private boolean isWorkDay(LocalDate date) {
		return !isWeekEnd(date) && !isHoliday(date);
	}

	private boolean isWeekEnd(LocalDate date) {
		DayOfWeek dow = date.getDayOfWeek();
		return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
	}

	private boolean isHoliday(LocalDate date) {
		return getHolidays().contains(date);
	}

	private List<LocalDate> getHolidays() {
		if (holidays.isEmpty()) {
			feriadoRepository.findAll().forEach(it -> holidays.add(it.getData()));
		}
		return holidays;
	}
}
