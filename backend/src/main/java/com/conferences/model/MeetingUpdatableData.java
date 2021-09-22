package com.conferences.model;

import com.conferences.deserializer.MeetingUpdatableDataDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
@JsonDeserialize(using = MeetingUpdatableDataDeserializer.class)
public class MeetingUpdatableData {

    private int id;

    @Size(min = 5)
    private String address;

    @Future
    private LocalDateTime date;
}
