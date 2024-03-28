package com.app.services;    /*
 *created by WerWolfe on DateServices
 */

import com.app.config.HolidaysConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j(topic = "Date services")
@Service
@RequiredArgsConstructor
public class DateServices {

    private final HolidaysConfig holidaysConfig;
    private final HashMap<Integer, Set<LocalDate>> holidayDates = new HashMap<>();

    public int countDays(LocalDate start, LocalDate end, Predicate<LocalDate> condition) {

        if (start.getYear() < 1900 || end.getYear() < 1900) {
            log.error("invalid date");
            return 0;
        }

        int countDays = 0;
        LocalDate date = start;

        while (date.compareTo(end) < 1) {
            if (condition.test(date)) {
                countDays++;
            }
            date = date.plusDays(1);
        }
        return countDays;
    }

    public int countWorkDays(LocalDate start, LocalDate end, boolean onlyHoliday) {
        Predicate<LocalDate> condition = onlyHoliday ? this::isHoliday : this::isWeekendOrHoliday;
        return countDays(start, end, condition.negate());
    }

    public int countWeekendDays(LocalDate start, LocalDate end) {
        return countDays(start, end, this::isWeekend);
    }

    public int countHolidays(LocalDate start, LocalDate end) {
        return countDays(start, end, this::isHoliday);
    }

    private boolean isWeekendOrHoliday(LocalDate date) {
        return isWeekend(date) || isHoliday(date);
    }

    public int countWorkDays(@NonNull LocalDate start, @NonNull LocalDate end) {
        return countWorkDays(start, end, false);
    }

    public boolean isHoliday(@NonNull LocalDate date) {

        int years = date.getYear();

        if (!holidayDates.containsKey(years)) {
            fillHolidayDates(years);
        }
        return holidayDates
                .get(years)
                .contains(date);
    }

    public boolean isWeekend(@NonNull LocalDate date) {

        boolean weekend = date.getDayOfWeek().getValue() > 5;
        boolean holiday = isHoliday(date);

        return weekend || holiday;
    }

    public Set<LocalDate> getHolidayDates(int year) {

        if (!holidayDates.containsKey(year)) {
            fillHolidayDates(year);
        }
        return holidayDates.get(year);
    }

    private void fillHolidayDates(int ...years) {

        if (years.length == 0 || holidaysConfig.getDays() == null) {
            return;
        }

        Arrays.stream(years)
                .filter(year -> year < 1900 || !holidayDates.containsKey(year))
                .forEach(year -> holidayDates.put(year, getHolidaysByYears(year)));
    }

    private Set<LocalDate> getHolidaysByYears(int year) {

        return Arrays.stream(holidaysConfig.getHolidays())
                .map(day -> LocalDate.of(year, day[1], day[0]))
                .collect(Collectors.toSet());
    }
}
