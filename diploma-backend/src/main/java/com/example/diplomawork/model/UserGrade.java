package com.example.diplomawork.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_grade")
@Builder
public class UserGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User student;

    private Integer firstGrade;

    private Integer finalGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defence_id", nullable = false)
    private Defence defence;
}
