package com.conferences.model;

import com.conferences.deserializer.MeetingSorterDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MeetingSorterDeserializer.class)
public class MeetingSorter {
    private Sort sort;
    private DateFilter dateFilter;
    /*private String sortBy;
    private String sortOrder;
    private String select;*/
}
