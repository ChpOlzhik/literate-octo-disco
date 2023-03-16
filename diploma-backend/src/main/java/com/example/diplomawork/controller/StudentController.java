package com.example.diplomawork.controller;

import com.example.api.StudentApi;
import com.example.diplomawork.service.StorageService;
import com.example.diplomawork.service.StudentService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudentController implements StudentApi {

    private final StudentService studentService;

    private final StorageService storageService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Override
    public ResponseEntity<Void> createTeam(TeamCreateUpdateRequest request) {
        logger.info("Team create| Contest participation request: " + request.getName());
        studentService.createUpdateTeam(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateTeam(TeamCreateUpdateRequest request){
        logger.info("Team update| Set presentation URL: " + request.getName());
        studentService.createUpdateTeam(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TeamInfoWithMembersDto> getTeam() {
        logger.info("Get team info");
        return ResponseEntity.ok(studentService.getTeam());
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAndSetPresentation(MultipartFile file){
        logger.info("upload presentation");
        return ResponseEntity.ok(storageService.uploadParticipantPresentation(file));
    }

    @Override
    public ResponseEntity<List<UserGradeDto>> getGrades(){
        logger.info("Get grades for stages");
        List<UserGradeDto> grades = studentService.getGrades();
        return ResponseEntity.ok(grades);
    }

}
