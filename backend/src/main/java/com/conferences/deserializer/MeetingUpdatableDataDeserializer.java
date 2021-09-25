package com.conferences.deserializer;

import com.conferences.handler.abstraction.IDateHandler;
import com.conferences.model.MeetingUpdatableData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
public class MeetingUpdatableDataDeserializer extends JsonDeserializer<MeetingUpdatableData> {

    private final IDateHandler dateHandler;

    @Autowired
    public MeetingUpdatableDataDeserializer(IDateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    @Override
    public MeetingUpdatableData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        log.info("Deserializing JSON to {}", MeetingUpdatableData.class.getTypeName());
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        MeetingUpdatableData meeting = new MeetingUpdatableData();

        meeting.setId(node.get("id").intValue());
        meeting.setAddress(node.get("address").textValue());
        meeting.setDate(dateHandler.parseDateFromJsonNode(node));

        return meeting;
    }
}
