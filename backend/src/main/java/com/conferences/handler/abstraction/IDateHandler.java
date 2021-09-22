package com.conferences.handler.abstraction;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;

public interface IDateHandler {

    LocalDateTime parseDateFromJsonNode(JsonNode node);
}
