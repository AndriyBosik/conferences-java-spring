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
@AllArgsConstructor
public class ReportTopicSpeaker {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "report_topic_id")
    private Integer reportTopicId;

    /*@Column(name = "speaker_id")
    private Integer speakerId;*/

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id")
    private User speaker;
}
