package com.example.diplomawork.controller;

import com.example.api.SecretaryApi;
import com.example.diplomawork.model.Announcement;
import com.example.diplomawork.service.AnnouncementService;
import com.example.diplomawork.service.SecretaryService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SecretaryController implements SecretaryApi {

    private final SecretaryService secretaryService;
    private final AnnouncementService announcementService;

    @Override
    public ResponseEntity<List<UserDto>> getDefenceCommissions(Long defenceId) {
        return ResponseEntity.ok(secretaryService.getDefenceCommission(defenceId));
    }

    @Override
    public ResponseEntity<DefenceInfoByBlocksDto> getSecretaryDefence(Long defenceId) {
        return ResponseEntity.ok(secretaryService.getDefenceInfo(defenceId));
    }

    @Override
    public ResponseEntity<List<DefenceShortInfoDto>> getSecretaryDefences() {
        return ResponseEntity.ok(secretaryService.getDefencesInfo());
    }

    @Override
    public ResponseEntity<List<StudentWithGradeDto>> getStudentsWithGrades(Long defenceId) {
        return ResponseEntity.ok(secretaryService.getStudentsWithGrades(defenceId));
    }

    @Override
    public ResponseEntity<Void> setFinalGrade(Long defenceId, Long userId, GradeDto gradeDto) {
        secretaryService.setFinalGrade(defenceId, userId, gradeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AnnouncementDto>> getAnnouncements(){
        return ResponseEntity.ok(announcementService.getAnnouncements());
    }

    @Override
    public ResponseEntity<AnnouncementDto> getAnnouncement(Long announcementId){
        return ResponseEntity.ok(announcementService.getAnnouncement(announcementId));
    }

    @Override
    public ResponseEntity<Void> deleteAnnouncement(Long announcement_id){
        announcementService.deleteAnnouncement(announcement_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createAnnouncement(AnnouncementCreateUpdateRequest request){
        announcementService.createUpdateAnnouncement(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateAnnouncement(AnnouncementCreateUpdateRequest request){

        announcementService.createUpdateAnnouncement(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
