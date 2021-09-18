package com.conferences.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users_meetings")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMeeting {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "meeting_id")
    private Integer meetingId;

    @Column(name = "present")
    private Boolean present;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;
}
