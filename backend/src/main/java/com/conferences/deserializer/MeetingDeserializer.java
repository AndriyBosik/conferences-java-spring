package com.conferences.deserializer;

import com.conferences.entity.Meeting;
import com.conferences.handler.abstraction.IDateHandler;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class MeetingDeserializer extends JsonDeserializer<Meeting> {

    private final IDateHandler dateHandler;

    @Autowired
    public MeetingDeserializer(IDateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    @Override
    public Meeting deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Meeting meeting = new Meeting();
        if (node.get("id") != null) {
            meeting.setId(node.get("id").intValue());
        }

        if (node.get("title") != null) {
            meeting.setTitle(node.get("title").textValue());
        }
        if (node.get("description") != null) {
            meeting.setDescription(node.get("description").textValue());
        }
        meeting.setAddress(node.get("address").textValue());
        meeting.setDate(dateHandler.parseDateFromJsonNode(node));

        return meeting;
    }
}