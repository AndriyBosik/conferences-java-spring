package com.conferences.factory.abstraction;

import com.conferences.model.DateFilter;

/**
 * <p>Configures an instance of {@link DateFilter} class</p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IDateFilterFactory {

    /**
     * <p>Produces instance of {@link DateFilter} class based on filter value</p>
     * @param select filter value
     * @return instance of {@link DateFilter}
     */
    DateFilter getDateFilter(String select);
}
