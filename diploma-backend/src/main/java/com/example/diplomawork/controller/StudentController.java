package com.example.diplomawork.controller;

import com.example.api.StudentApi;
import com.example.diplomawork.model.UserGrade;
import com.example.diplomawork.service.StorageService;
import com.example.diplomawork.service.StudentService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudentController implements StudentApi {

    private final StudentService studentService;

    private final StorageService storageService;

    @Override
    public ResponseEntity<Void> createTeam(TeamCreateUpdateRequest request) {
        studentService.createUpdateTeam(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TeamInfoWithMembersDto> getTeam() {
        return ResponseEntity.ok(studentService.getTeam());
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAndSetPresentation(MultipartFile file){
        return ResponseEntity.ok(storageService.uploadParticipantPresentation(file));
    }

    @Override
    public ResponseEntity<List<UserGradeDto>> getGrades(){
        List<UserGradeDto> grades = studentService.getGrades();
        return ResponseEntity.ok(grades);
    }

}
