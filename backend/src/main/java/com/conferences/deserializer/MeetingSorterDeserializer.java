package com.conferences.deserializer;

import com.conferences.factory.abstraction.IDateFilterFactory;
import com.conferences.factory.abstraction.ISortFactory;
import com.conferences.model.MeetingSorter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MeetingSorterDeserializer extends JsonDeserializer<MeetingSorter> {

    private final IDateFilterFactory dateFilterFactory;
    private final ISortFactory sortFactory;
    
    @Autowired
    public MeetingSorterDeserializer(IDateFilterFactory dateFilterFactory, ISortFactory sortFactory) {
        this.dateFilterFactory = dateFilterFactory;
        this.sortFactory = sortFactory;
    }

    @Override
    public MeetingSorter deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        MeetingSorter meetingSorter = new MeetingSorter();
        meetingSorter.setDateFilter(dateFilterFactory.getDateFilter(node.get("select").textValue()));
        meetingSorter.setSort(sortFactory.getSort(
            node.get("sortBy").textValue(),
            node.get("sortOrder").textValue()
        ));

        return meetingSorter;
    }
}
