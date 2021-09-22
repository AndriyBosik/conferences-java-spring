package com.conferences.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "report_topics")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportTopic {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 5)
    @Column(name = "title")
    private String title;

    @Column(name = "meeting_id")
    private Integer meetingId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private ReportTopicSpeaker reportTopicSpeaker;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_topic_id")
    private Set<SpeakerProposal> speakerProposals = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_topic_id")
    private Set<ModeratorProposal> moderatorProposals = new HashSet<>();
}
