package com.example.diplomawork.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subjects")
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<User> subjectUsers;

    public Subject(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}