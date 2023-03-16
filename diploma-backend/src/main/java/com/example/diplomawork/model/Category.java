package com.example.diplomawork.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "categories")
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameKaz;
    private String nameRus;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<User> categoryUsers;

    public Category(Long id, String nameKaz, String nameRus) {
        this.id = id;
        this.nameKaz = nameKaz;
        this.nameRus = nameRus;
    }

}