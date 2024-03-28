package com.app.contollers;    /*
 *created by WerWolfe on DefaultController
 */

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    private final String defaultMessage;

    {
        defaultMessage = "Для продолжения отправьте GET запрос по адресу localHost/api со следующими параметрами: "
                + System.lineSeparator() +
                "average_monthly_earnings = средняя месячная зарплата за 12 месяцев" + System.lineSeparator() +
                "vacation_days = количество дней отпуска"
                + System.lineSeparator() +
                "или эти параметры: "
                + System.lineSeparator() +
                "average_monthly_earnings = средняя месячная зарплата за 12 месяцев"
                + System.lineSeparator() +
                "start_date = начало отпуска" + System.lineSeparator() +
                "end_date = конец отпуска";
    }

    @RequestMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok(defaultMessage);
    }
}
