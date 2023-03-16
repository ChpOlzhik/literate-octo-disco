package com.example.diplomawork.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teams")
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team")
    private User creator;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    List<Defence> teamDefences;

    private Boolean confirmed;

    private String presentationURL;

    private String lessonRecordingURL;

    private String applicationFormURL;


    public Team(Long id, @NonNull String name, User creator, Boolean confirmed, String presentationURL, String lessonRecordingURL, String applicationFormURL) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.confirmed = confirmed;
        this.presentationURL = presentationURL;
        this.lessonRecordingURL = lessonRecordingURL;
        this.applicationFormURL = applicationFormURL;
    }
}
