package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IDateHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Log4j2
@Component
public class DateHandler implements IDateHandler {

    private final String dateFormat;

    @Autowired
    public DateHandler(@Value("${date.format}") String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public LocalDateTime parseDateFromJsonNode(JsonNode node) {
        log.info("Parsing JSON date to {}", LocalDateTime.class.getTypeName());
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
