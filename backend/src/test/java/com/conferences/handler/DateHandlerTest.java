package com.conferences.handler;

import com.conferences.handler.abstraction.IDateHandler;
import com.conferences.handler.implementation.DateHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DateHandlerTest {

    private static DateHandler dateHandler;

    @BeforeAll
    private static void beforeAll() {
        dateHandler = new DateHandler("dd-MM-yyyy HH:mm");
    }

    @Test
    public void shouldParseJsonNodeValueToDate() {
        JsonNode node = Mockito.mock(JsonNode.class);

        JsonNode dateNode = Mockito.mock(JsonNode.class);
        Mockito.when(dateNode.textValue()).thenReturn("29-09-2021");

        JsonNode hoursNode = Mockito.mock(JsonNode.class);
        Mockito.when(hoursNode.textValue()).thenReturn("5");

        JsonNode minutesNode = Mockito.mock(JsonNode.class);
        Mockito.when(minutesNode.textValue()).thenReturn("10");

        Mockito.when(node.get("date")).thenReturn(dateNode);
        Mockito.when(node.get("hours")).thenReturn(hoursNode);
        Mockito.when(node.get("minutes")).thenReturn(minutesNode);

        LocalDateTime ldt = dateHandler.parseDateFromJsonNode(node);

        assertEquals(29, ldt.getDayOfMonth());
        assertEquals(9, ldt.getMonthValue());
        assertEquals(2021, ldt.getYear());
        assertEquals(5, ldt.getHour());
        assertEquals(10, ldt.getMinute());
    }
}
