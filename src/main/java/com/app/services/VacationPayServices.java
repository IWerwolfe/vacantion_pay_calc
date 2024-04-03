package com.app.services;    /*
 *created by WerWolfe on VacationPayServices
 */

import com.app.config.VacationConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@Service
public class VacationPayServices {

    private final VacationConfig vacationConfig;
    private final DateServices dateServices;

    public VacationPayServices(VacationConfig vacationConfig, DateServices dateServices) {

        if (vacationConfig == null ||
                vacationConfig.getAverageCalendarDaysPerMonth() == null ||
                vacationConfig.getPersonalIncomeTaxRate() == null) {
            throw new RuntimeException("Vacation setting not set");
        }

        this.vacationConfig = vacationConfig;
        this.dateServices = dateServices;
    }

    public BigDecimal calcVacationPay(@NonNull String averageMonthlyEarnings,
                                      @NonNull String start,
                                      @NonNull String end) {

        return calcVacationPay(
                new BigDecimal(averageMonthlyEarnings),
                LocalDate.parse(start),
                LocalDate.parse(end));
    }

    public BigDecimal calcVacationPay(@NonNull String averageMonthlyEarnings,
                                      @NonNull String start,
                                      @NonNull String end,
                                      boolean withTax) {


        return calcVacationPay(
                new BigDecimal(averageMonthlyEarnings),
                LocalDate.parse(start),
                LocalDate.parse(end),
                withTax);
    }

    public BigDecimal calcVacationPay(@NonNull String vacationDays, @NonNull String averageMonthlyEarnings) {

        return calcVacationPay(
                new BigDecimal(vacationDays),
                new BigDecimal(averageMonthlyEarnings));
    }

    public BigDecimal calcVacationPay(@NonNull String vacationDays, @NonNull String averageMonthlyEarnings, boolean withTax) {

        return calcVacationPay(
                new BigDecimal(vacationDays),
                new BigDecimal(averageMonthlyEarnings),
                withTax);
    }

    public BigDecimal calcVacationPay(@NonNull BigDecimal vacationDays, @NonNull BigDecimal averageMonthlyEarnings) {
        return calcVacationPay(vacationDays, averageMonthlyEarnings, false);
    }

    public BigDecimal calcVacationPay(@NonNull BigDecimal vacationDays, @NonNull BigDecimal averageMonthlyEarnings, boolean withTax) {

        if (invalidData(vacationDays, averageMonthlyEarnings)) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = averageMonthlyEarnings
                .divide(vacationConfig.getAverageCalendarDaysPerMonth(), MathContext.DECIMAL64)
                .multiply(vacationDays);

        if (withTax) {
            BigDecimal tax = result.multiply(vacationConfig.getPersonalIncomeTaxRate());
            return result
                    .subtract(tax)
                    .setScale(vacationConfig.getPrecision(), RoundingMode.CEILING);
        }
        return result
                .setScale(vacationConfig.getPrecision(), RoundingMode.CEILING);
    }

    public BigDecimal calcVacationPay(@NonNull BigDecimal averageMonthlyEarnings,
                                      @NonNull LocalDate start,
                                      @NonNull LocalDate end) {

        return calcVacationPay(averageMonthlyEarnings, start, end, false);
    }

    public BigDecimal calcVacationPay(@NonNull BigDecimal averageMonthlyEarnings,
                                      @NonNull LocalDate start,
                                      @NonNull LocalDate end,
                                      boolean withTax) {

        int holidays = dateServices.countHolidays(start, end);
        int actualDays = dateServices.countWorkDays(start, end, true);

        if (holidays > 0) {
            log.error("{} holiday(s) falls on vacation", holidays);
        }

        return calcVacationPay(new BigDecimal(actualDays), averageMonthlyEarnings, withTax);
    }

    private boolean invalidData(BigDecimal vacationDays, BigDecimal averageMonthlyEarnings) {

        if (vacationDays.equals(BigDecimal.ZERO) || averageMonthlyEarnings.equals(BigDecimal.ZERO)) {
            log.error("Vacation days or average monthly earnings is zero");
            return true;
        }
        return false;
    }
}
