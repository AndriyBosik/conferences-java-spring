package com.conferences.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "moderator_proposals")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeratorProposal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "speaker_id")
    private Integer speakerId;

    @Column(name = "report_topic_id")
    private int reportTopicId;
}
