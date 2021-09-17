package com.conferences.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "topic_proposals")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicProposal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "meeting_id")
    private Integer meetingId;

    @Column(name = "topic_title")
    private String topicTitle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User speaker;
}
