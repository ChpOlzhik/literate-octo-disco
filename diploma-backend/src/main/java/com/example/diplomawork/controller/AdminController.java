package com.example.diplomawork.controller;

import com.example.api.AdminApi;
import com.example.diplomawork.service.AdminService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Override
    public ResponseEntity<Void> createCommission(CreateCommissionMemberRequest request) {
        logger.info("Commission create attempt: " + request.getLastName());
        adminService.createUpdateCommissionMember(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateCommission(CreateCommissionMemberRequest request) {
        logger.info("Commission create attempt: " + request.getLastName());
        adminService.createUpdateCommissionMember(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDto>> getCommissions() {
        return ResponseEntity.ok(adminService.getCommissions());
    }

    @Override
    public ResponseEntity<List<StageDto>> getStages() {
        return ResponseEntity.ok(adminService.getStages());
    }

    @Override
    public ResponseEntity<Void> createDefence(Long teamId, CreateDefenceRequest request) {
        adminService.createDefence(teamId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        adminService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserInfoByBlocksDto> getUser(Long userId) {
        return ResponseEntity.ok(adminService.getUser(userId));
    }

    @Override
    public ResponseEntity<Void> createGroup(GroupDto groupDto) {
        adminService.createUpdateGroup(groupDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteGroupInfo(Long groupId) {
        adminService.deleteGroup(groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupInfoByBlocksDto> getGroupInfo(Long groupId) {
        return ResponseEntity.ok(adminService.getGroupInfo(groupId));
    }

    @Override
    public ResponseEntity<Void> updateGroupInfo(GroupDto groupDto) {
        adminService.createUpdateGroup(groupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createStage(StageDto stageDto) {
        adminService.createUpdateStage(stageDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteStage(Long stageId) {
        adminService.deleteStage(stageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StageInfoByBlocksDto> getStage(Long stageId) {
        return ResponseEntity.ok(adminService.getStageInfo(stageId));
    }

    @Override
    public ResponseEntity<Void> updateStage(StageDto stageDto) {
        adminService.createUpdateStage(stageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createSubject(SubjectDto subjectDto) {
        adminService.createUpdateSubject(subjectDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteSubjectInfo(Long subjectId) {
        adminService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SubjectInfoByBlocksDto> getSubjectInfo(Long subjectId) {
        return ResponseEntity.ok(adminService.getSubjectInfo(subjectId));
    }

    @Override
    public ResponseEntity<Void> updateSubjectInfo(SubjectDto subjectDto) {
        adminService.createUpdateSubject(subjectDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
