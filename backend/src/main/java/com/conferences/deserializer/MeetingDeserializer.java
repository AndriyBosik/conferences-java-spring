package com.conferences.deserializer;

import com.conferences.entity.Meeting;
import com.conferences.handler.abstraction.IDateHandler;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Log4j2
public class MeetingDeserializer extends JsonDeserializer<Meeting> {

    private final IDateHandler dateHandler;

    @Autowired
    public MeetingDeserializer(IDateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    @Override
    public Meeting deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        log.info("Deserializing JSON to meeting");
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
