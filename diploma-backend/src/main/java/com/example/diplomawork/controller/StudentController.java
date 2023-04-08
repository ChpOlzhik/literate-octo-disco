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

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Override
    public ResponseEntity<Void> updatePresentationURL(SetUrlRequest request){
        logger.info("Team update | Set presentation URL: " + request.getUrl());
        studentService.updatePresentationURL(request.getUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateArticleURL(SetUrlRequest request){
        logger.info("Team update | Set presentation URL: " + request.getUrl());
        studentService.updatePresentationURL(request.getUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TeamInfoWithMemberDto> getTeam() {
        logger.info("Get team info");
        return ResponseEntity.ok(studentService.getTeam());
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAndSetPresentation(MultipartFile file){
        logger.info("upload presentation");
        return ResponseEntity.ok(storageService.uploadParticipantPresentation(file));
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAndSetArticle(MultipartFile file){
        logger.info("upload article");
        return ResponseEntity.ok(storageService.uploadParticipantArticle(file));
    }

    @Override
    public ResponseEntity<List<UserGradeDto>> getGrades(){
        logger.info("Get grades for stages");
        List<UserGradeDto> grades = studentService.getGrades();
        return ResponseEntity.ok(grades);
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAndSetApplicationForm(MultipartFile file){
        logger.info("Application form upload request");
        return ResponseEntity.ok(storageService.uploadParticipantApplicationForm(file));
    }

}
