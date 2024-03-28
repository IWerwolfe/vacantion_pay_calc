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

    public BigDecimal calcVacationPay(@NonNull BigDecimal vacationDays, @NonNull BigDecimal mediumSalary) {

        if (invalidData(vacationDays, mediumSalary)) {
            return BigDecimal.ZERO;
        }

        return mediumSalary
                .divide(vacationConfig.getAverageCalendarDaysPerMonth(), MathContext.DECIMAL64)
                .multiply(vacationDays)
                .setScale(vacationConfig.getPrecision(), RoundingMode.CEILING);
    }

    public BigDecimal calcVacationPay(@NonNull BigDecimal vacationDays,
                                      @NonNull BigDecimal mediumSalary,
                                      @NonNull LocalDate start,
                                      @NonNull LocalDate end) {

        if (invalidData(vacationDays, mediumSalary)) {
            return BigDecimal.ZERO;
        }

        int workDays = dateServices.countWorkDays(start.minusYears(1).minusDays(1), start, true);
        int actualDays = dateServices.countWorkDays(start, end, true);

        return mediumSalary
                .multiply(new BigDecimal(12))
                .divide(new BigDecimal(workDays), MathContext.DECIMAL64)
                .multiply(new BigDecimal(actualDays))
                .setScale(vacationConfig.getPrecision(), RoundingMode.CEILING);
    }

    private boolean invalidData(BigDecimal vacationDays, BigDecimal mediumSalary) {

        if (vacationDays.equals(BigDecimal.ZERO) || mediumSalary.equals(BigDecimal.ZERO)) {
            log.error("Vacation days or medium salary is zero");
            return true;
        }

        return false;
    }
}
