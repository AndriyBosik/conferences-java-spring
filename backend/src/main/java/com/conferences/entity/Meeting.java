package com.conferences.entity;

import com.conferences.deserializer.MeetingDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meetings")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MeetingDeserializer.class)
public class Meeting {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 5)
    @Column(name = "title")
    private String title;

    @Size(min = 10)
    @Column(name = "description")
    private String description;

    @Size(min = 5)
    @Column(name = "address")
    private String address;

    @Column(name = "image_path")
    private String imagePath;

    @Future
    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Set<ReportTopic> reportTopics = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<TopicProposal> topicProposals = new HashSet<>();
}
