package com.example.diplomawork.model;


import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_commission_grade")
@Builder
public class UserCommissionGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commission_id", nullable = false)
    private User commission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defence_id", nullable = false)
    private Defence defence;

    @ManyToOne(fetch = FetchType.LAZY)
    private Criteria criteria;

    private Integer grade;
}
