package com.conferences.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "speaker_proposals")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeakerProposal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "speaker_id")
    private Integer speakerId;

    @Column(name = "report_topic_id")
    private Integer reportTopicId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id", updatable = false, insertable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User speaker;
}
