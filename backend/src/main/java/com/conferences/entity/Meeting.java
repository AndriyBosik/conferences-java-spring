package com.conferences.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "meetings")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "image_path")
    private String imagePath;

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
