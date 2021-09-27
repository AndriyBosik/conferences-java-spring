package com.conferences.mapper;

/**
 * <p>
 *     Defines method to map one object to another
 * </p>
 * @param <T> represents type of object to map from
 * @param <U> represents type of object to map to
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IMapper<T, U> {

    /**
     * <p>Maps one object to another</p>
     * @param model contains data to be mapper from
     * @return object with mapped values
     */
    U map(T model);
}
