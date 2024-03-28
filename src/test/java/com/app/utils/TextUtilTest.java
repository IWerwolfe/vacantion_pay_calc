package com.app.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
 *created by WerWolfe on
 */
@SpringBootTest
class TextUtilTest {

    @Autowired
    private TextUtil textUtil;

    boolean result;

    @Test
    void stringsIsCorrect_valid() {

        result = textUtil.stringsIsCorrect("test");
        assertTrue(result);

        result = textUtil.stringsIsCorrect("test", "test1 ", "test2");
        assertTrue(result);

    }

    @Test
    void stringsIsCorrect_invalidParams() {

        result = textUtil.stringsIsCorrect(null, "test1", null);
        assertFalse(result);

        result = textUtil.stringsIsCorrect(null);
        assertFalse(result);

        result = textUtil.stringsIsCorrect("null", "test1", "");
        assertFalse(result);

        result = textUtil.stringsIsCorrect("");
        assertFalse(result);

        result = textUtil.stringsIsCorrect("    ");
        assertFalse(result);
    }

    @Test
    void stringsIsNumeric_valid() {

        result = textUtil.isNumeric("1");
        assertTrue(result);

        result = textUtil.isNumeric("1", "123", "123.45");
        assertTrue(result);
    }

    @Test
    void stringsIsNumeric_invalidParams() {

        result = textUtil.isNumeric(null, "123", "==");
        assertFalse(result);

        result = textUtil.isNumeric(null);
        assertFalse(result);

        result = textUtil.isNumeric("");
        assertFalse(result);

        result = textUtil.isNumeric("dfdg");
        assertFalse(result);

        result = textUtil.isNumeric("    ");
        assertFalse(result);

        result = textUtil.isNumeric("1245,67");
        assertFalse(result);

        result = textUtil.isNumeric("1245-67");
        assertFalse(result);
    }

    @Test
    void stringsIsDate_valid() {

        result = textUtil.isDate("2024-12-01");
        assertTrue(result);
        assertEquals(LocalDate.of(2024, 12, 1), LocalDate.parse("2024-12-01"));

        result = textUtil.isDate("2024-12-01", "2000-01-01", "2224-12-01");
        assertTrue(result);
        assertEquals(LocalDate.of(2024, 12, 1), LocalDate.parse("2024-12-01"));
        assertEquals(LocalDate.of(2000, 1, 1), LocalDate.parse("2000-01-01"));
        assertEquals(LocalDate.of(2224, 12, 1), LocalDate.parse("2224-12-01"));
    }

    @Test
    void stringsIsDate_invalidParams() {

        result = textUtil.isDate(null, "2024-12-1", "==");
        assertFalse(result);

        result = textUtil.isDate(null);
        assertFalse(result);

        result = textUtil.isDate("");
        assertFalse(result);

        result = textUtil.isDate("dfdg");
        assertFalse(result);

        result = textUtil.isDate("    ");
        assertFalse(result);

        result = textUtil.isDate("2024-12-1");
        assertFalse(result);

        result = textUtil.isDate("2024-1-01");
        assertFalse(result);

        result = textUtil.isDate("2024-12-42");
        assertFalse(result);

        result = textUtil.isDate("2024-12-123");
        assertFalse(result);

        result = textUtil.isDate("2024-45-01");
        assertFalse(result);
    }
}