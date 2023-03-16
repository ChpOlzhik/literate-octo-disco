package com.example.diplomawork.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "groups")
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameKaz;
    private String nameRus;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<User> groupUsers;

    public Group(Long id, String nameKaz, String nameRus) {
        this.id = id;
        this.nameKaz = nameKaz;
        this.nameRus = nameRus;
    }
}
