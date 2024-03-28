package com.app.services;

import com.app.config.HolidaysConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 *created by WerWolfe on
 */
@SpringBootTest
class DateServicesTest {

    @Autowired
    private HolidaysConfig holidayConfig;

    @Autowired
    private DateServices dateServices;

    private int currentYear = LocalDate.now().getYear();

    @Test
    void countWorkDays_invalidDate() {

        LocalDate start = LocalDate.of(1900, 1,1);
        LocalDate end = LocalDate.of(1, 1, 1);
        int workDate = dateServices.countWorkDays(start, end);
        assertEquals(0, workDate);
    }

    @Test
    void countWorkDays_validHolidaysOneYear() {

        LocalDate start = LocalDate.of(2024, 1,1);
        LocalDate end = LocalDate.of(2024, 12,31);;
        int holidays = dateServices.countHolidays(start, end);
        assertEquals(14, holidays);
    }

    @Test
    void countWorkDays_validWeekendDaysOneMonth() {

        LocalDate start = LocalDate.of(2024, 1,1);
        LocalDate end = LocalDate.of(2024, 1,31);;
        int holidays = dateServices.countWeekendDays(start, end);
        assertEquals(14, holidays);
    }


    @Test
    void countWorkDays_validDateOneMoth() {

        LocalDate start = LocalDate.of(currentYear, 1,1);
        LocalDate end = LocalDate.of(currentYear, 1, 31);
        int workDate = dateServices.countWorkDays(start, end);
        assertEquals(17, workDate);
    }

    @Test
    void countWorkDays_validDateOneMothOnlyHolidays() {

        LocalDate start = LocalDate.of(currentYear, 1,1);
        LocalDate end = LocalDate.of(currentYear, 1, 31);
        int workDate = dateServices.countWorkDays(start, end, true);
        assertEquals(23, workDate);
    }

    @Test
    void isHoliday_validDate() {

        LocalDate date = LocalDate.of(currentYear, 1,3);
        LocalDate date1 = LocalDate.of(currentYear, 5,9);

        boolean isHoliday1 = dateServices.isHoliday(date);
        boolean isHoliday2 = dateServices.isHoliday(date1);

        assertTrue(isHoliday1);
        assertTrue(isHoliday2);
    }

    @Test
    void isHoliday_invalidDate() {

        LocalDate date = LocalDate.of(currentYear, 1,30);
        LocalDate date1 = LocalDate.of(currentYear, 3,25);

        boolean isHoliday1 = dateServices.isHoliday(date);
        boolean isHoliday2 = dateServices.isHoliday(date1);

        assertFalse(isHoliday1);
        assertFalse(isHoliday2);
    }

    @Test
    void isWeekend_validDate() {

        LocalDate date = LocalDate.of(currentYear, 5,9);
        LocalDate date1 = LocalDate.of(currentYear, 3,30);

        boolean isHoliday1 = dateServices.isWeekend(date);
        boolean isHoliday2 = dateServices.isWeekend(date1);

        assertTrue(isHoliday1);
        assertTrue(isHoliday2);
    }

    @Test
    void isWeekend_invalidDate() {

        LocalDate date = LocalDate.of(currentYear, 1,30);
        LocalDate date1 = LocalDate.of(currentYear, 3,25);

        boolean isHoliday1 = dateServices.isWeekend(date);
        boolean isHoliday2 = dateServices.isWeekend(date1);

        assertFalse(isHoliday1);
        assertFalse(isHoliday2);
    }

    @Test
    void getHolidayDates_years() {

        Set<LocalDate> dateList = dateServices.getHolidayDates(2024);

        assertNotNull(dateList);
        assertEquals(14, dateList.size());
    }
}