package com.app.contollers;    /*
 *created by WerWolfe on ApiController
 */

import com.app.services.VacationPayServices;
import com.app.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final VacationPayServices vacationPayServices;
    private final TextUtil textUtil;

    @GetMapping("/calculate")
    public ResponseEntity<String> responseVacationPay(@RequestParam("average_monthly_earnings") String averageMonthlyEarnings,
                                                      @RequestParam(value = "vacation_days", defaultValue = "") String vacationDays,
                                                      @RequestParam(value = "start_date", defaultValue = "") String startDate,
                                                      @RequestParam(value = "end_date", defaultValue = "") String endDate) {

        String text = "";
        if (textUtil.isDate(startDate, endDate) && textUtil.isNumeric(averageMonthlyEarnings)) {
            text = "Calculate started, request params: average monthly earnings = {}, start date = {} and end date = {}";
            log.debug(text, averageMonthlyEarnings, startDate, endDate);
            return ResponseEntity.ok(vacationPayServices.calcVacationPay(averageMonthlyEarnings, startDate, endDate).toString());
        }

        if (textUtil.isNumeric(vacationDays, averageMonthlyEarnings)) {
            text = "Calculate started, request params: average monthly earnings = {}, vacation days = {}";
            log.debug(text, averageMonthlyEarnings, vacationDays);
            return ResponseEntity.ok(vacationPayServices.calcVacationPay(vacationDays, averageMonthlyEarnings).toString());
        }

        text = "Invalid parameters entered: " +
                "average monthly earnings = {}, " +
                "start date = {} and end date = {}, " +
                "vacation days = {}";
        log.error(text, averageMonthlyEarnings, startDate, endDate, vacationDays);

        return ResponseEntity.status(400).body("Invalid parameters entered");
    }
}
