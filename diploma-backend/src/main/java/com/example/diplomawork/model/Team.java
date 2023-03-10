package com.example.diplomawork.model;

import lombok.*;

import javax.persistence.*;
import javax.sound.sampled.ReverbType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_creator_id")
    private User creator;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    List<UserTeam> userTeams;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    List<Defence> teamDefences;

    private Boolean confirmed;


    public Team(Long id, @NonNull String name, User creator, Boolean confirmed) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.confirmed = confirmed;
    }
}
