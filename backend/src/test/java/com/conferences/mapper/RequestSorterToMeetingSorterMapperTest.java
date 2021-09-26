package com.conferences.mapper;

import com.conferences.factory.implementation.DateFilterFactory;
import com.conferences.factory.implementation.SortFactory;
import com.conferences.model.MeetingSorter;
import com.conferences.model.RequestSorter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class RequestSorterToMeetingSorterMapperTest {

    private static RequestSorterToMeetingSorterMapper mapper;

    @BeforeAll
    private static void beforeAll() {
        mapper = new RequestSorterToMeetingSorterMapper(new DateFilterFactory(), new SortFactory());
    }

    @Test
    public void shouldMapRequestSorterToFutureAscMeetingSorter() {
        RequestSorter requestSorter = new RequestSorter();
        requestSorter.setSortBy("date");
        requestSorter.setSelect("future");
        requestSorter.setSortOrder("asc");

        MeetingSorter sorter = mapper.map(requestSorter);

        Sort sort = JpaSort.unsafe(Sort.Direction.valueOf("ASC"), "(date)");
        assertEquals(sort, sorter.getSort());

        long minDiff = Timestamp.valueOf(LocalDateTime.now()).getTime() - sorter.getDateFilter().getMin().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(minDiff) <= 1);

        long maxDiff = Timestamp.valueOf(LocalDateTime.now().plusYears(1000)).getTime() - sorter.getDateFilter().getMax().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(maxDiff) <= 1);
    }

    @Test
    public void shouldMapRequestSorterToPastAscMeetingSorter() {
        RequestSorter requestSorter = new RequestSorter();
        requestSorter.setSortBy("topics_count");
        requestSorter.setSelect("past");
        requestSorter.setSortOrder("asc");

        MeetingSorter sorter = mapper.map(requestSorter);

        Sort sort = JpaSort.unsafe(Sort.Direction.valueOf("ASC"), "(topics_count)");
        assertEquals(sort, sorter.getSort());

        long minDiff = new Timestamp(Integer.MIN_VALUE).getTime() - sorter.getDateFilter().getMin().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(minDiff) <= 1);

        long maxDiff = Timestamp.valueOf(LocalDateTime.now()).getTime() - sorter.getDateFilter().getMax().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(maxDiff) <= 1);
    }

    @Test
    public void shouldMapRequestSorterToAllDescMeetingSorter() {
        RequestSorter requestSorter = new RequestSorter();
        requestSorter.setSortBy("users_count");
        requestSorter.setSelect("all");
        requestSorter.setSortOrder("desc");

        MeetingSorter sorter = mapper.map(requestSorter);

        Sort sort = JpaSort.unsafe(Sort.Direction.valueOf("DESC"), "(users_count)");
        assertEquals(sort, sorter.getSort());

        long minDiff = new Timestamp(Integer.MIN_VALUE).getTime() - sorter.getDateFilter().getMin().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(minDiff) <= 1);

        long maxDiff = Timestamp.valueOf(LocalDateTime.now().plusYears(1000)).getTime() - sorter.getDateFilter().getMax().getTime();
        assertTrue(TimeUnit.MILLISECONDS.toMinutes(maxDiff) <= 1);
    }
}
