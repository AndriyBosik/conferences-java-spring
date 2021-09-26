package com.conferences.factory;

import com.conferences.factory.implementation.SortFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import static org.junit.jupiter.api.Assertions.*;

public class SortFactoryTest {

    private static SortFactory factory;

    @BeforeAll
    private static void beforeAll() {
        factory = new SortFactory();
    }

    @Test
    public void shouldReturnAscSortByDateColumn() {
        Sort sort = factory.getSort("date", "asc");
        Sort expectedSort = JpaSort.unsafe(Sort.Direction.ASC, "(date)");

        assertEquals(expectedSort, sort);
    }

    @Test
    public void shouldReturnDescSortByTopicsCountColumn() {
        Sort sort = factory.getSort("topics_count", "desc");
        Sort expectedSort = JpaSort.unsafe(Sort.Direction.DESC, "(topics_count)");

        assertEquals(expectedSort, sort);
    }

    @Test
    public void shouldReturnAscSortByIdColumn() {
        Sort sort = factory.getSort("", "asc");
        Sort expectedSort = JpaSort.unsafe(Sort.Direction.ASC, "(id)");

        assertEquals(expectedSort, sort);
    }
}
