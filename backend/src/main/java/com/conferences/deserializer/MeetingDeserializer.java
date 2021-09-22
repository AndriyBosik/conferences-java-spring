package com.conferences.deserializer;

import com.conferences.entity.Meeting;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MeetingDeserializer extends JsonDeserializer<Meeting> {

    @Value("${date.format}")
    private String dateFormat;

    @Override
    public Meeting deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Meeting meeting = new Meeting();

        meeting.setTitle(node.get("title").textValue());
        meeting.setDescription(node.get("description").textValue());
        meeting.setAddress(node.get("address").textValue());
        meeting.setDate(parseDate(node));

        return meeting;
    }

    private LocalDateTime parseDate(JsonNode node) {
        String strDate =    node.get("date").textValue() + " " +
                            addZeroToBegin(node.get("hours").textValue()) + ":" +
                            addZeroToBegin(node.get("minutes").textValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.UK);
        return LocalDateTime.parse(strDate, formatter);
    }

    private String addZeroToBegin(String value) {
        if (value == null) {
            return "00";
        }
        if (value.length() < 2) {
            return "0" + value;
        }
        return value;
    }
}
