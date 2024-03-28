package com.app.config;    /*
 *created by WerWolfe on VacationConfig
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "holidays")
@PropertySource("classpath:application.yml")
public class HolidaysConfig {

    private List<String> days;
    private int[][] holidays;

    @PostConstruct
    public void init() {

        holidays = new int[days.size()][2];

        this.holidays = days.stream()
                .map(HolidaysConfig::getArr)
                .filter(Objects::nonNull)
                .toArray(int[][]::new);
    }

    private static int[] getArr(String day) {
        String[] dayArr = day.split("\\.");
        return dayArr.length == 2 ?
                new int[]{Integer.parseInt(dayArr[0]), Integer.parseInt(dayArr[1])} :
                null;
    }
}
