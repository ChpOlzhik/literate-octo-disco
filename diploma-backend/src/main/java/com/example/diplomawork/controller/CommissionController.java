package com.example.diplomawork.controller;

import com.example.api.CommissionApi;
import com.example.diplomawork.service.CommissionService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommissionController implements CommissionApi {

    private final CommissionService commissionService;


    @Override
    public ResponseEntity<StudentWithGradeDto> getStudentsWithCommissionGrades(Long defenceId) {
        return ResponseEntity.ok(commissionService.getStudentsWithCommissionGrades(defenceId));
    }

    @Override
    public ResponseEntity<Void> setGrade(Long defenceId, Long studentId, GradeDto gradeDto) {
        commissionService.setGrade(defenceId, studentId, gradeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DefenceInfoByBlocksDto> getCommissionDefence(Long defenceId) {
        return ResponseEntity.ok(commissionService.getCommissionDefence(defenceId));
    }


    @Override
    public ResponseEntity<List<DefenceShortInfoDto>> getCommissionDefences() {
        return ResponseEntity.ok(commissionService.getCommissionDefences());
    }
}
