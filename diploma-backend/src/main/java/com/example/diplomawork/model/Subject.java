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

    private String nameKaz;
    private String nameRus;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<User> subjectUsers;

    public Subject(Long id, String nameKaz, String nameRus) {
        this.id = id;
        this.nameKaz = nameKaz;
        this.nameRus = nameRus;
    }

}