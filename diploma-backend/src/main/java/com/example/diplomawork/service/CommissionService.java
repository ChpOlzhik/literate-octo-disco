package com.example.diplomawork.service;

import com.example.diplomawork.mapper.CriteriaMapper;
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

    private final CriteriaRepository criteriaRepository;

    private final StageRepository stageRepository;

    private final CriteriaMapper criteriaMapper;

    public DefenceInfoByBlocksDto getCommissionDefence(Long defenceId) {
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        Team team = defence.getTeam();
        TeamInfoWithMemberDto teamInfo = TeamInfoWithMemberDto.builder()
                .team(teamMapper.entity2dto(team))
                .build();

        return DefenceInfoByBlocksDto.builder()
                .defence(defenceMapper.entity2dto(defence))
                .team(teamInfo)
                .build();
    }


    public void setGrade(Long defenceId, List<GradeCriteriaDto> gradeDtos) {
        User commission = authService.getCurrentUser();
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        User student = defence.getTeam().getCreator();
        gradeDtos.forEach(gradeCriteriaDto -> {
            Criteria criteria = criteriaRepository.findById(gradeCriteriaDto.getCriteria()).orElseThrow(() -> new EntityNotFoundException("No such criteria :" + gradeCriteriaDto.getCriteria()));
            UserCommissionGrade userCommissionGrade = userCommissionGradeRepository.findByCommissionIdAndStudentIdAndDefenceIdAndCriteriaId(commission.getId(), student.getId(), defence.getId(), gradeCriteriaDto.getCriteria());
            if (userCommissionGrade == null) {
                userCommissionGrade = UserCommissionGrade.builder()
                        .id(null)
                        .commission(commission)
                        .defence(defence)
                        .student(defence.getTeam().getCreator())
                        .criteria(criteria)
                        .build();
            }

            userCommissionGrade.setGrade(gradeCriteriaDto.getGrade());
            userCommissionGradeRepository.saveAndFlush(userCommissionGrade);
        });
    }

    public List<CriteriaDto> getCriteries(Long stageId){
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotFoundException("Stage not found with id: " + stageId));
        return  stage.getCriteries().stream().map(criteriaMapper::entity2dto).collect(Collectors.toList());
    }

    public List<DefenceShortInfoDto> getCommissionDefences() {
        User currentUser = authService.getCurrentUser();
        List<DefenceCommission> defenceCommissions = defenceCommissionRepository.findDefenceCommissionsByCommissionId(currentUser.getId());
        List<DefenceShortInfoDto> list = new ArrayList<>();
        defenceCommissions.forEach(defenceCommission -> {
            Defence defence = defenceCommission.getDefence();
            List<UserCommissionGrade> grades = userCommissionGradeRepository.findAllByDefenceIdAndCommissionId(defence.getId(), currentUser.getId());

            DefenceShortInfoDto build = DefenceShortInfoDto.builder()
                    .id(defence.getId())
                    .defenceDate(defenceCommission.getDefence().getDefenceDate())
                    .team(defenceCommission.getDefence().getTeam().getName())
                    .stage(defence.getStage().getName())
                    .grades(grades.stream().map(grade->
                            GradeCriteriaDto.builder().criteria(grade.getCriteria().getId()).grade(grade.getGrade()).build()).collect(Collectors.toList()))
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
        List<UserCommissionGrade> grades = userCommissionGradeRepository.findAllByCommissionIdAndStudentIdAndDefenceId(commission.getId(), student.getId(), defenceId);
        return StudentWithGradeDto.builder()
                .fullName(student.getLastName() + student.getFirstName())
                .stageName(defence.getStage().getName())
                .grade(grades.stream().map(grade->
                        GradeCriteriaDto.builder().criteria(grade.getCriteria().getId()).grade(grade.getGrade()).build()).collect(Collectors.toList()))
                .build();
    }
}
