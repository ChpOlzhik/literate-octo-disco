package com.example.diplomawork.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "districts")
@Builder
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameKaz;
    private String nameRus;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<Group> districtGroups;

    public District(Long id, String nameKaz, String nameRus) {
        this.id = id;
        this.nameKaz = nameKaz;
        this.nameRus = nameRus;
    }

}
