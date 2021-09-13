package com.conferences.factory.implementation;

import com.conferences.factory.abstraction.ISortFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Component;

@Component
public class SortFactory implements ISortFactory {

    @Override
    public Sort getSort(String column, String order) {
        return JpaSort.unsafe(Sort.Direction.valueOf(order), "(" + column + ")");
    }
}
