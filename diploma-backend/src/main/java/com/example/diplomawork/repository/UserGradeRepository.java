package com.example.diplomawork.repository;

import com.example.diplomawork.model.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {
    UserGrade findByDefenceIdAndStudentId(Long defenceId, Long studentId);
}
