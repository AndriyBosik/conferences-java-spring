package com.conferences.factory.implementation;

import com.conferences.factory.abstraction.IDateFilterFactory;
import com.conferences.model.DateFilter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 */
@Component
public class DateFilterFactory implements IDateFilterFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public DateFilter getDateFilter(String select) {
        DateFilter dateFilter = new DateFilter(
            new Timestamp(Integer.MIN_VALUE),
            Timestamp.valueOf(LocalDateTime.now().plusYears(1000))
        );
        if (select == null) {
            return dateFilter;
        }
        if (select.trim().equalsIgnoreCase("future")) {
            dateFilter.setMin(Timestamp.valueOf(LocalDateTime.now()));
        } else if (select.trim().equalsIgnoreCase("past")) {
            dateFilter.setMax(Timestamp.valueOf(LocalDateTime.now()));
        }
        return dateFilter;
    }
}
