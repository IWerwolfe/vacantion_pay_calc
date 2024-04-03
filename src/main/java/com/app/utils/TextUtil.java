package com.app.utils;    /*
 *created by WerWolfe on TextUtil
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
public class TextUtil {

    private final String patternNumeric = "[0-9\\.]*";
    private final String patternDate = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

    public boolean stringsIsCorrect(String... args) {
        return args != null && Arrays.stream(args)
                .noneMatch(arg -> Objects.isNull(arg) || arg.trim().isEmpty());
    }

    public boolean stringsMatches(String pattern, String... args) {
        return args != null && Arrays.stream(args)
                .allMatch(arg -> stringsIsCorrect(arg) && arg.trim().matches(pattern));
    }

    public boolean isNumeric(String... args) {
        return stringsMatches(patternNumeric, args);
    }

    public boolean isDate(String... args) {
        return stringsMatches(patternDate, args);
    }
}
