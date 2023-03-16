package com.example.diplomawork.service;

import com.example.diplomawork.mapper.DefenceMapper;
import com.example.diplomawork.mapper.TeamMapper;
import com.example.diplomawork.mapper.UserMapper;
import com.example.diplomawork.model.*;
import com.example.diplomawork.repository.*;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommissionService {

    private final DefenceRepository defenceRepository;

    private final UserRepository userRepository;

    private final DefenceCommissionRepository defenceCommissionRepository;

    private final UserCommissionGradeRepository userCommissionGradeRepository;

    private final AuthService authService;

    private final TeamMapper teamMapper;

    private final UserMapper userMapper;

    private final DefenceMapper defenceMapper;

    public DefenceInfoByBlocksDto getCommissionDefence(Long defenceId) {
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        Team team = defence.getTeam();
        TeamInfoWithMemberDto teamInfo = TeamInfoWithMemberDto.builder()
                .team(teamMapper.entity2dto(team))
                .member(userMapper.entity2dto(team.getCreator()))
                .build();

        return DefenceInfoByBlocksDto.builder()
                .defence(defenceMapper.entity2dto(defence))
                .team(teamInfo)
                .build();
    }


    public void setGrade(Long defenceId, Long studentId, GradeDto grade) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("User with id: " + studentId + " not found"));
        User commission = authService.getCurrentUser();
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        UserCommissionGrade userCommissionGrade = userCommissionGradeRepository.findByCommissionIdAndStudentIdAndDefenceId(commission.getId(), student.getId(), defence.getId());
        if (userCommissionGrade == null) {
            userCommissionGrade = UserCommissionGrade.builder()
                    .id(null)
                    .commission(commission)
                    .defence(defence)
                    .student(student)
                    .build();
        }
        userCommissionGrade.setGrade(grade.getGrade());
        userCommissionGradeRepository.save(userCommissionGrade);
    }

    public List<DefenceShortInfoDto> getCommissionDefences() {
        User currentUser = authService.getCurrentUser();
        List<DefenceCommission> defenceCommissions = defenceCommissionRepository.findDefenceCommissionsByCommissionId(currentUser.getId());
        List<DefenceShortInfoDto> list = new ArrayList<>();
        defenceCommissions.forEach(defenceCommission -> {
            Defence defence = defenceCommission.getDefence();
            DefenceShortInfoDto build = DefenceShortInfoDto.builder()
                    .id(defence.getId())
                    .defenceDate(defenceCommission.getDefence().getDefenceDate())
                    .team(defenceCommission.getDefence().getTeam().getName())
                    .stage(defence.getStage().getName())
                    .build();
            list.add(build);
        });
        return list;
    }

    public List<UserDto> getDefenceCommissions(Long defenceId) {
        List<DefenceCommission> defenceCommissions = defenceCommissionRepository.findDefenceCommissionsByDefenceId(defenceId);
        return defenceCommissions.stream().map(defence -> userMapper.entity2dto(defence.getCommission())).collect(Collectors.toList());
    }

    // TODO
    public StudentWithGradeDto getStudentsWithCommissionGrades(Long defenceId) {
        User commission = authService.getCurrentUser();
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        User student = defence.getTeam().getCreator();
        UserCommissionGrade grade = userCommissionGradeRepository.findByCommissionIdAndStudentIdAndDefenceId(commission.getId(), student.getId(), defenceId);
        return StudentWithGradeDto.builder()
                .fullName(student.getLastName() + student.getFirstName())
                .stageName(defence.getStage().getName())
                .grade(grade.getGrade())
                .build();
    }
}
