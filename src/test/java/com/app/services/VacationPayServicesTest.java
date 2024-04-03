package com.app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
    void calcVacationPay_inValidData() {

        BigDecimal result;

        result = payServices.calcVacationPay(BigDecimal.ZERO, new BigDecimal(1200000));
        assertNotNull(result);
        assertEquals(result, BigDecimal.ZERO);

        result = payServices.calcVacationPay(new BigDecimal(12), BigDecimal.ZERO);
        assertNotNull(result);
        assertEquals(result, BigDecimal.ZERO);

        assertThrows(RuntimeException.class,
                () -> new VacationPayServices(null, dateServices),
                "Vacation setting not set");
    }

    @Test
    void calcVacationPay_isCorrect() {
        BigDecimal days = new BigDecimal(28);
        BigDecimal mediumSalary = new BigDecimal(100000);
        BigDecimal result = payServices.calcVacationPay(days, mediumSalary);
        BigDecimal resultTax = payServices.calcVacationPay(days, mediumSalary, true);

        assertNotNull(result);
        assertNotNull(resultTax);
        assertEquals(result, new BigDecimal("95563.140"));
        assertEquals(resultTax, new BigDecimal("83139.932"));
    }

    @Test
    void calcVacationPayInPeriod_isCorrect() {

        BigDecimal mediumSalary;
        LocalDate start;
        LocalDate end;
        BigDecimal result;
        BigDecimal resultTax;

        mediumSalary = new BigDecimal(50000);
        start = LocalDate.of(2024, 6, 1);
        end = LocalDate.of(2024, 6, 14);

        result = payServices.calcVacationPay(mediumSalary, start, end);
        resultTax = payServices.calcVacationPay(mediumSalary, start, end, true);

        assertNotNull(result);
        assertNotNull(resultTax);
        assertEquals(result, new BigDecimal("22184.301"));
        assertEquals(resultTax, new BigDecimal("19300.342"));

        start = LocalDate.of(2024, 1, 9);
        end = LocalDate.of(2024, 1, 29);

        result = payServices.calcVacationPay(mediumSalary, start, end);
        resultTax = payServices.calcVacationPay(mediumSalary, start, end, true);

        assertNotNull(result);
        assertNotNull(resultTax);
        assertEquals(result, new BigDecimal("35836.178"));
        assertEquals(resultTax, new BigDecimal("31177.475"));
    }
}