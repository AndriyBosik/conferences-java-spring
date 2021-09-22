package com.conferences.deserializer;

import com.conferences.handler.abstraction.IDateHandler;
import com.conferences.model.MeetingUpdatableData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class MeetingUpdatableDataDeserializer extends JsonDeserializer<MeetingUpdatableData> {

    private final IDateHandler dateHandler;

    @Autowired
    public MeetingUpdatableDataDeserializer(IDateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    @Override
    public MeetingUpdatableData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        MeetingUpdatableData meeting = new MeetingUpdatableData();

        meeting.setId(node.get("id").intValue());
        meeting.setAddress(node.get("address").textValue());
        meeting.setDate(dateHandler.parseDateFromJsonNode(node));

        return meeting;
    }
}
