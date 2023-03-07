package com.example.diplomawork.controller;

import com.example.api.AdminApi;
import com.example.diplomawork.service.AdminService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;


    @Override
    public ResponseEntity<Void> createCommission(CreateCommissionMemberRequest request) {
        adminService.createUpdateCommissionMember(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateCommission(CreateCommissionMemberRequest request) {
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
    public ResponseEntity<List<TeamShortInfoDto>> getTeams() {
        return ResponseEntity.ok(adminService.getTeams());
    }

    @Override
    public ResponseEntity<Void> deleteTeam(Long teamId) {
        adminService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        adminService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TeamInfoByBlocksDto> getTeamInfo(Long teamId) {
        return ResponseEntity.ok(adminService.getTeamInfo(teamId));
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
}
