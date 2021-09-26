package com.conferences.factory;

import com.conferences.factory.implementation.DateFilterFactory;
import com.conferences.model.DateFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class DateFilterFactoryTest {

    private static DateFilterFactory factory;

    @BeforeAll
    private static void beforeAll() {
        factory = new DateFilterFactory();
    }

    @Test
    public void shouldReturnFullRangeForSpecifiedValue() {
        DateFilter filter = factory.getDateFilter("all");

        long minDiff = new Timestamp(Integer.MIN_VALUE).getTime() - filter.getMin().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(minDiff) <= 1);

        long maxDiff = Timestamp.valueOf(LocalDateTime.now().plusYears(1000)).getTime() - filter.getMax().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(maxDiff) <= 1);
    }

    @Test
    public void shouldReturnFullRangeForNullValue() {
        DateFilter filter = factory.getDateFilter(null);

        long minDiff = new Timestamp(Integer.MIN_VALUE).getTime() - filter.getMin().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(minDiff) <= 1);

        long maxDiff = Timestamp.valueOf(LocalDateTime.now().plusYears(1000)).getTime() - filter.getMax().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(maxDiff) <= 1);
    }

    @Test
    public void shouldReturnFutureRange() {
        DateFilter filter = factory.getDateFilter("future");

        long minDiff = Timestamp.valueOf(LocalDateTime.now()).getTime() - filter.getMin().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(minDiff) <= 1);

        long maxDiff = Timestamp.valueOf(LocalDateTime.now().plusYears(1000)).getTime() - filter.getMax().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(maxDiff) <= 1);
    }

    @Test
    public void shouldReturnPastRange() {
        DateFilter filter = factory.getDateFilter("past");

        long minDiff = new Timestamp(Integer.MIN_VALUE).getTime() - filter.getMin().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(minDiff) <= 1);

        long maxDiff = Timestamp.valueOf(LocalDateTime.now()).getTime() - filter.getMax().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(maxDiff) <= 1);
    }
}
