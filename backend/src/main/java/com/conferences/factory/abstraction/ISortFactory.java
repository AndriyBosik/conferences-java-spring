package com.conferences.factory.abstraction;

import com.conferences.model.DateFilter;
import org.springframework.data.domain.Sort;

/**
 * <p>Creates {@link Sort} object</p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface ISortFactory {

    /**
     * <p>Produces instance of {@link DateFilter} class based on sorting values</p>
     * @param column sorting column
     * @param order sorting order
     * @return an instance of {@link Sort} class
     */
    Sort getSort(String column, String order);
}
