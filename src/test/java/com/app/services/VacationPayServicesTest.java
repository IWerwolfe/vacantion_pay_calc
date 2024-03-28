package com.app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 *created by WerWolfe on *
 */
@SpringBootTest
class VacationPayServicesTest {

    @Autowired
    private VacationPayServices payServices;
    @Autowired
    private DateServices dateServices;

    @Test
    void calcVacationPay_inValidVacationDays() {
        BigDecimal result = payServices.calcVacationPay(BigDecimal.ZERO, new BigDecimal(1200000));
        assertEquals(result, BigDecimal.ZERO);
    }

    @Test
    void calcVacationPay_inValidMediumSalary() {
        BigDecimal result = payServices.calcVacationPay(new BigDecimal(12), BigDecimal.ZERO);
        assertEquals(result, BigDecimal.ZERO);
    }

    @Test
    void calcVacationPay_invalidVacationSetting() {
        assertThrows(RuntimeException.class,
                () -> new VacationPayServices(null, dateServices),
                "Vacation setting not set");
    }

    @Test
    void calcVacationPay_isCorrect() {
        BigDecimal days = new BigDecimal(28);
        BigDecimal mediumSalary = new BigDecimal(100000);
        BigDecimal result = payServices.calcVacationPay(days, mediumSalary);
        assertEquals(result, new BigDecimal("95563.140"));
    }

    @Test
    void calcVacationPayInPeriod_isCorrect() {
        BigDecimal days = new BigDecimal(14);
        BigDecimal mediumSalary = new BigDecimal(50000);
        LocalDate start = LocalDate.of(2024, 6, 1);
        LocalDate end = LocalDate.of(2024, 6, 14);

        BigDecimal result = payServices.calcVacationPay(days, mediumSalary, start, end);

        assertEquals(result, new BigDecimal("23890.785"));
    }
}