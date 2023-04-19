package com.example.diplomawork.repository;

import com.example.diplomawork.model.UserCommissionGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCommissionGradeRepository extends JpaRepository<UserCommissionGrade, Long> {
    Optional<UserCommissionGrade> findByCommissionIdAndStudentId(Long commissionId, Long studentId);

    List<UserCommissionGrade> findAllByDefenceIdAndStudentId(Long defenceId, Long studentId);

    List<UserCommissionGrade> findAllByStudentId(Long studentId);

    List<UserCommissionGrade> findAllByCommissionIdAndStudentIdAndDefenceId(Long commissionId, Long studentId, Long defenceId);

    UserCommissionGrade findByCommissionIdAndStudentIdAndDefenceIdAndCriteriaId(Long commissionId, Long studentId, Long defenceId, Long criteriaId);

    void deleteAllByDefenceId(Long defenceId);
}
