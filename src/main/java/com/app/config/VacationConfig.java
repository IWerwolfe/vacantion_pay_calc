package com.app.config;    /*
 *created by WerWolfe on VacationConfig
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "vacation")
public class VacationConfig {

    private BigDecimal averageCalendarDaysPerMonth;
    private BigDecimal personalIncomeTaxRate;
    private int precision;

}
