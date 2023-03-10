package com.example.diplomawork.service;

import com.example.diplomawork.mapper.DefenceMapper;
import com.example.diplomawork.mapper.StageMapper;
import com.example.diplomawork.mapper.TeamMapper;
import com.example.diplomawork.mapper.UserMapper;
import com.example.diplomawork.model.*;
import com.example.diplomawork.repository.*;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecretaryService {

    private final AuthService authService;

    private final DefenceRepository defenceRepository;

    private final UserTeamRepository userTeamRepository;

    private final TeamRepository teamRepository;

    private final UserGradeRepository userGradeRepository;

    private final UserCommissionGradeRepository userCommissionGradeRepository;

    private final DefenceCommissionRepository defenceCommissionRepository;
    private final UserRepository userRepository;

    private final TeamMapper teamMapper;

    private final UserMapper userMapper;

    private final DefenceMapper defenceMapper;

    private final StageMapper stageMapper;


    public List<TeamShortInfoDto> getTeams() {
        List<Team> teams = teamRepository.findAllByConfirmedTrue();
        return teams.stream().map(team -> TeamShortInfoDto.builder()
                .id(team.getId())
                .name(team.getName())
                .build()).collect(Collectors.toList());
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }


    public TeamInfoByBlocksDto getTeamInfo(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id: " + teamId + " not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByTeamIdAndAcceptedTrue(teamId);
        List<UserDto> members = userTeams.stream().map(user -> userMapper.entity2dto(user.getUser())).collect(Collectors.toList());
        List<DefenceDto> defences = team.getTeamDefences().stream().map(defence -> DefenceDto.builder()
                .id(defence.getId())
                .defenceDate(defence.getDefenceDate())
                .stage(stageMapper.entity2dto(defence.getStage()))
                .build()).collect(Collectors.toList());
        return TeamInfoByBlocksDto.builder()
                .team(teamMapper.entity2dto(team))
                .creator(userMapper.entity2dto(team.getCreator()))
                .defences(defences)
                .members(members)
                .build();
    }

    public void setTeamLessonRecording(String lessonRecordingURL, Long teamId){
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id: " + teamId + " not found"));
        team.setLessonRecordingURL(lessonRecordingURL);
        teamRepository.save(team);
    }

    public DefenceInfoByBlocksDto getDefenceInfo(Long defenceId) {
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        Team team = defence.getTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByTeamIdAndAcceptedTrue(team.getId());
        TeamInfoWithMembersDto teamInfo = TeamInfoWithMembersDto.builder()
                .team(teamMapper.entity2dto(team))
                .members(userTeams.stream().map(userTeam -> userMapper.entity2dto(userTeam.getUser())).collect(Collectors.toList()))
                .build();

        return DefenceInfoByBlocksDto.builder()
                .defence(defenceMapper.entity2dto(defence))
                .team(teamInfo)
                .build();
    }

    public List<DefenceShortInfoDto> getDefencesInfo() {
        List<Defence> defences = defenceRepository.findAll();
        List<DefenceShortInfoDto> dtos = new ArrayList<>();
        defences.forEach(defence -> {
            DefenceShortInfoDto dto = DefenceShortInfoDto.builder()
                    .id(defence.getId())
                    .defenceDate(defence.getDefenceDate())
                    .team(defence.getTeam().getName())
                    .stage(defence.getStage().getName())
                    .build();
            dtos.add(dto);
        });
        return dtos;
    }

    public List<StudentWithGradeDto> getStudentsWithGrades(Long defenceId) {
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByTeamIdAndAcceptedTrue(defence.getTeam().getId());
        List<StudentWithGradeDto> students = new ArrayList<>();
        userTeams.forEach(userTeam -> {
            List<UserCommissionGrade> grades = userCommissionGradeRepository.findAllByDefenceIdAndStudentId(defenceId, userTeam.getUser().getId());
            students.add(StudentWithGradeDto.builder()
                    .id(userTeam.getUser().getId())
                    .fullName(userTeam.getUser().getFirstName() + " " + userTeam.getUser().getLastName())
                    .grade(userTeam.getUser().getGrade() != null ? userTeam.getUser().getGrade().getFinalGrade() : (!grades.isEmpty() ? grades.stream().mapToInt(UserCommissionGrade::getGrade).sum() / grades.size() : null))
                    .build());
        });
        return students;
    }

    public void setFinalGrade(Long defenceId, Long userId, GradeDto gradeDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
        List<UserCommissionGrade> grades = userCommissionGradeRepository.findAllByDefenceIdAndStudentId(defenceId, userId);
        if (user.getGrade() != null) {
            user.getGrade().setFirstGrade(grades.stream().mapToInt(UserCommissionGrade::getGrade).sum() / grades.size());
            user.getGrade().setFinalGrade(gradeDto.getGrade());
            userRepository.save(user);
        } else {
            UserGrade userGrade = UserGrade.builder()
                    .id(null)
                    .firstGrade(!grades.isEmpty() ? grades.stream().mapToInt(UserCommissionGrade::getGrade).sum() / grades.size() : null)
                    .finalGrade(gradeDto.getGrade())
                    .student(user)
                    .build();
            userGradeRepository.save(userGrade);
        }
    }

    public List<UserDto> getDefenceCommission(Long defenceId) {
        List<DefenceCommission> defenceCommissions = defenceCommissionRepository.findDefenceCommissionsByDefenceId(defenceId);
        return defenceCommissions.stream().map(defenceCommission -> userMapper.entity2dto(defenceCommission.getCommission())).collect(Collectors.toList());
    }

}
