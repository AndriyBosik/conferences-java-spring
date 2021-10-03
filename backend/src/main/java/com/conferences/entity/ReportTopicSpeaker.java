package com.conferences.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "report_topics_speakers")
@Getter @Setter
@NoArgsConstructor
public class ReportTopicSpeaker {

    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
    @Column(name = "report_topic_id")
    private Integer reportTopicId;

    @Column(name = "speaker_id")
    private Integer speakerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id", updatable = false, insertable = false)
    private User speaker;

    public ReportTopicSpeaker(int id, int reportTopicId, int speakerId) {
        this.id = id;
        this.reportTopicId = reportTopicId;
        this.speakerId = speakerId;
    }
}
