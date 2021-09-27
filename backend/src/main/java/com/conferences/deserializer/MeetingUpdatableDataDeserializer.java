package com.conferences.deserializer;

import com.conferences.handler.abstraction.IDateHandler;
import com.conferences.model.MeetingUpdatableData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * <p>
 *     Deserializes JSON to {@link MeetingUpdatableData} object
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
public class MeetingUpdatableDataDeserializer extends JsonDeserializer<MeetingUpdatableData> {

    private final IDateHandler dateHandler;

    @Autowired
    public MeetingUpdatableDataDeserializer(IDateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    /**
     * <p>Deserialized JSON</p>
     * @param jsonParser provides forward, read-only access to JSON data in a streaming way
     * @param deserializationContext context for the process of deserialization a single root-level value
     * @return deserialized {@link MeetingUpdatableData} object
     * @throws IOException may be thrown while method executing
     */
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
