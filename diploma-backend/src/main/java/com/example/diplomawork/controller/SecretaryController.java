package com.example.diplomawork.controller;

import com.example.api.SecretaryApi;
import com.example.diplomawork.service.AnnouncementService;
import com.example.diplomawork.service.SecretaryService;
import com.example.diplomawork.service.StorageService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SecretaryController implements SecretaryApi {

    private final SecretaryService secretaryService;
    private final AnnouncementService announcementService;

    private final StorageService storageService;

    @Override
    public ResponseEntity<UsersNumberDto> getUsersNumber(){
        return ResponseEntity.ok(secretaryService.getUsersAmount());
    }

    @Override
    public ResponseEntity<List<UserDto>> getDefenceCommissions(Long defenceId) {
        return ResponseEntity.ok(secretaryService.getDefenceCommission(defenceId));
    }

    @Override
    public ResponseEntity<DefenceInfoByBlocksDto> getSecretaryDefence(Long defenceId) {
        return ResponseEntity.ok(secretaryService.getDefenceInfo(defenceId));
    }

    @Override
    public ResponseEntity<Void> updateDefence(Long defenceId, CreateDefenceRequest request){
        secretaryService.updateDefence(defenceId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteDefence(Long defenceId){
        secretaryService.deleteDefence(defenceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> createDefence(Long teamId, CreateDefenceRequest request) {
        secretaryService.createDefence(teamId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DefenceShortInfoDto>> getSecretaryDefences() {
        return ResponseEntity.ok(secretaryService.getDefencesInfo());
    }

    @Override
    public ResponseEntity<StudentWithGradesDto> getStudentWithGrades(Long defenceId) {
        return ResponseEntity.ok(secretaryService.getStudentWithGrades(defenceId));
    }

    @Override
    public ResponseEntity<Void> setFinalGrade(Long defenceId, Long userId, GradeDto gradeDto) {
        secretaryService.setFinalGrade(defenceId, userId, gradeDto);
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(AnnouncementCreateUpdateRequest request){
        return ResponseEntity.ok(announcementService.createUpdateAnnouncement(request));
    }

    @Override
    public ResponseEntity<Void> updateAnnouncement(AnnouncementCreateUpdateRequest request){
        announcementService.createUpdateAnnouncement(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAnnouncementFile(Long announcementId, MultipartFile file){
        FileUploadResponse announcementFileURL = storageService.uploadAndSetAnnouncementFile(file, announcementId);
        return ResponseEntity.ok(announcementFileURL);
    }

    @Override
    public ResponseEntity<List<TeamShortInfoDto>> getTeams(Boolean isConfirmed) {
        return ResponseEntity.ok(secretaryService.getTeams(isConfirmed));
    }

    @Override
    public ResponseEntity<Void> deleteTeam(Long teamId) {
        secretaryService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TeamInfoByBlocksDto> getTeamInfo(Long teamId) {
        return ResponseEntity.ok(secretaryService.getTeamInfo(teamId));
    }

    @Override
    public ResponseEntity<Void> setTeamLessonRecording(Long teamId, SetLessonRecordingRequest request){
        secretaryService.setTeamLessonRecording(request.getLessonRecordingURL(), teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommissionDto>> getCommissions(Long subjectId) {
        return ResponseEntity.ok(secretaryService.getCommissions(subjectId));
    }

    @Override
    public ResponseEntity<Void> updateTeamConfirmation(Long teamId, Boolean confirmed){
        secretaryService.updateTeamConfirmation(teamId, confirmed);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
