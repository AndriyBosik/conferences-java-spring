package com.conferences.factory.abstraction;

import org.springframework.data.domain.Sort;

public interface ISortFactory {

    Sort getSort(String column, String order);
}
