package com.conferences.factory.implementation;

import com.conferences.factory.abstraction.ISortFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class SortFactory implements ISortFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Sort getSort(String column, String order) {
        if ("".equals(column)) {
            column = "id";
        }
        return JpaSort.unsafe(Sort.Direction.valueOf(order.toUpperCase()), "(" + column + ")");
    }
}
