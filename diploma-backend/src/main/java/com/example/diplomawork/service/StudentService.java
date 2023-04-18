package com.example.diplomawork.service;

import com.example.diplomawork.mapper.TeamMapper;
import com.example.diplomawork.mapper.UserMapper;
import com.example.diplomawork.model.Defence;
import com.example.diplomawork.model.Team;
import com.example.diplomawork.model.User;
import com.example.diplomawork.model.UserCommissionGrade;
import com.example.diplomawork.repository.DefenceRepository;
import com.example.diplomawork.repository.TeamRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.repository.UserCommissionGradeRepository;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final AuthService authService;

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    private final DefenceRepository defenceRepository;

    private final UserCommissionGradeRepository userCommissionGradeRepository;


    private final TeamMapper teamMapper;

    private final UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public void updatePresentationURL(String presentationURL){
        User currentUser = authService.getCurrentUser();
        Team team = currentUser.getTeam();
        team.setPresentationURL(presentationURL);
        logger.debug("Setting presentatio url for team:" + team.getId());
        teamRepository.save(team);
        logger.debug("Set presentatio url for team:" + team.getId());
    }

    public void updateArticleURL(String articleURL){
        User currentUser = authService.getCurrentUser();
        Team team = currentUser.getTeam();
        team.setArticleURL(articleURL);
        logger.debug("Setting article url for team:" + team.getId());
        teamRepository.save(team);
        logger.debug("Set article url for team:" + team.getId());
    }


    public TeamInfoWithMemberDto getTeam() {
        User currentUser = authService.getCurrentUser();
        Team team = currentUser.getTeam();
        logger.debug("Team Info for team: " + team.getName());
        return TeamInfoWithMemberDto.builder()
                .team(teamMapper.entity2dto(team))
                .build();
    }

    public List<UserGradeDto> getGrades(){
        User currentUser = authService.getCurrentUser();
        List<UserGradeDto> grades = new ArrayList<>();
        currentUser.getGrades().forEach(userGrade -> {
            grades.add(UserGradeDto.builder()
                    .grade(userGrade.getFirstGrade())
                    .stage(userGrade.getDefence().getStage().getName())
                    .build());
        });
        logger.debug(" Get grades for user: " + currentUser.getUsername());
        return grades;
    }

    public List<UserGradeFullDto> getGradesFull(Long stageId){
        User currentUser = authService.getCurrentUser();
        List <Defence> defences = defenceRepository.findAllByStageId(stageId);
        List <UserCommissionGrade> commissionGrades = new ArrayList<>();
        defences.forEach(defence -> {
            commissionGrades.addAll(userCommissionGradeRepository.findAllByDefenceIdAndStudentId(defence.getId(), currentUser.getId()));
        });

        Map<CommissionDto, List<GradeCriteriaDescDto>> mp = new HashMap<>();
        List<UserGradeFullDto> grades = new ArrayList<>();

        commissionGrades.forEach(commissionGrade -> {
            CommissionDto commission = userMapper.entity2CommissionDto(commissionGrade.getCommission());
            mp.putIfAbsent(commission, new ArrayList<>());
            mp.get(commission).add(GradeCriteriaDescDto.builder().grade(commissionGrade.getGrade()).criteria(commissionGrade.getCriteria().getDescription()).build());
        });

        mp.forEach((commissionDto, gradeCriteriaDescDtos) -> {
            grades.add(UserGradeFullDto.builder().commission(commissionDto).grades(gradeCriteriaDescDtos).build());
        });

        logger.debug(" Get grades for user: " + currentUser.getUsername());
        return grades;
    }



}
