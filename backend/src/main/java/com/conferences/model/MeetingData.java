package com.conferences.model;

import com.conferences.entity.Meeting;
import com.conferences.entity.projection.IUsersPresence;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingData {
    private Meeting meeting;
    private IUsersPresence usersPresence;
}
