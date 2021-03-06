package com.conferences.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

    @Column(name = "speaker_id")
    private Integer speakerId;

    @Column(name = "topic_title")
    @Size(min = 5)
    private String topicTitle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id", updatable = false, insertable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User speaker;
}
