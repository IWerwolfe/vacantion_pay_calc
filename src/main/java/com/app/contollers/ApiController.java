package com.app.contollers;    /*
 *created by WerWolfe on ApiController
 */

import com.app.services.VacationPayServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final VacationPayServices vacationPayServices;

    @GetMapping("/calculate")
    public ResponseEntity<String> responseVacationPay(@RequestParam("mediumSalary") String mediumSalary,
                                                         @RequestParam("vacationDays") String vacationDays) {

        log.debug("Calculate started, request params: medium salary = {}, vacation days = {}", mediumSalary, vacationDays);
        BigDecimal medium = new BigDecimal(mediumSalary);
        BigDecimal days = new BigDecimal(vacationDays);
        return ResponseEntity.ok(vacationPayServices.calcVacationPay(medium, days).toString());
    }
}
