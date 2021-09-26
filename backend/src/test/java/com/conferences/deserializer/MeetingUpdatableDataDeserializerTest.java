package com.conferences.deserializer;

import com.conferences.handler.abstraction.IDateHandler;
import com.conferences.model.MeetingUpdatableData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingUpdatableDataDeserializerTest {

    private static MeetingUpdatableDataDeserializer deserializer;
    private static DeserializationContext context;
    private static JsonParser jsonParser;

    @BeforeAll
    public static void beforeAll() throws IOException {
        deserializer = new MeetingUpdatableDataDeserializer(getDateHandlerMock());
        context = Mockito.mock(DeserializationContext.class);
        configureJsonParserMock();
    }

    @Test
    public void shouldParseUpdatableData() throws IOException {
        MeetingUpdatableData meeting = deserializer.deserialize(jsonParser, context);

        assertEquals(6, meeting.getId());
        assertEquals("Meeting address", meeting.getAddress());
        assertEquals(2021, meeting.getDate().getYear());
        assertEquals(10, meeting.getDate().getMonthValue());
        assertEquals(7, meeting.getDate().getDayOfMonth());
        assertEquals(15, meeting.getDate().getHour());
        assertEquals(30, meeting.getDate().getMinute());
    }

    private static IDateHandler getDateHandlerMock() {
        IDateHandler dateHandler = Mockito.mock(IDateHandler.class);
        Mockito.when(dateHandler.parseDateFromJsonNode(ArgumentMatchers.any(JsonNode.class))).thenReturn(
                LocalDateTime.of(2021, 10, 7, 15, 30));
        return dateHandler;
    }

    private static void configureJsonParserMock() throws IOException {
        jsonParser = Mockito.mock(JsonParser.class);
        ObjectCodec codec = Mockito.mock(ObjectCodec.class);
        JsonNode mockNode = configureJsonNode();
        Mockito.when(codec.readTree(jsonParser)).thenReturn(mockNode);
        Mockito.when(jsonParser.getCodec()).thenReturn(codec);
    }

    private static JsonNode configureJsonNode() {
        JsonNode node = Mockito.mock(JsonNode.class);

        JsonNode idNode = Mockito.mock(JsonNode.class);
        Mockito.when(idNode.intValue()).thenReturn(6);
        Mockito.when(node.get("id")).thenReturn(idNode);

        JsonNode addressNode = Mockito.mock(JsonNode.class);
        Mockito.when(addressNode.textValue()).thenReturn("Meeting address");
        Mockito.when(node.get("address")).thenReturn(addressNode);

        return node;
    }
}
