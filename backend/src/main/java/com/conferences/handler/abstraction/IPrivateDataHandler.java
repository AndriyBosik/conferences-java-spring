package com.conferences.handler.abstraction;

/**
 * <p>
 *     Defines methods to work with private data
 * </p>
 * @param <T> represents model with private data
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IPrivateDataHandler<T> {

    /**
     * <p>Clears private data of a model</p>
     * @param model an object to clear data for
     */
    void clearPrivateData(T model);
}
