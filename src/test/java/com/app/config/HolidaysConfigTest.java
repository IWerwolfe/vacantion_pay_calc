package com.app.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/*
 *created by WerWolfe on
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class HolidaysConfigTest {

    @Autowired
    private HolidaysConfig holidayConfig;

    @Test
    public void testGetHolidays() {

        int[][] example = new int[14][2];
        example[0] = new int[]{1, 1};
        example[1] = new int[]{2, 1};
        example[2] = new int[]{3, 1};
        example[3] = new int[]{4, 1};
        example[4] = new int[]{5, 1};
        example[5] = new int[]{6, 1};
        example[6] = new int[]{7, 1};
        example[7] = new int[]{8, 1};
        example[8] = new int[]{23, 2};
        example[9] = new int[]{8, 3};
        example[10] = new int[]{1, 5};
        example[11] = new int[]{9, 5};
        example[12] = new int[]{12, 6};
        example[13] = new int[]{4, 11};

        int[][] holidays = holidayConfig.getHolidays();
        assertNotNull(holidays);
        assertEquals(14, holidays.length);
        assertArrayEquals(example, holidays);

    }
}