package com.conferences.handler.abstraction;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;

/**
 * <p>
 *     Defines methods to process date
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IDateHandler {

    /**
     * <p>Parses JSON values to date</p>
     * @param node JsonNode which contains date values
     * @return an instance of {@link LocalDateTime} class which represents parsed date
     */
    LocalDateTime parseDateFromJsonNode(JsonNode node);
}
