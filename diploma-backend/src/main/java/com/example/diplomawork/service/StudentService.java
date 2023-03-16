package com.example.diplomawork.service;

import com.example.diplomawork.mapper.TeamMapper;
import com.example.diplomawork.mapper.UserMapper;
import com.example.diplomawork.model.Team;
import com.example.diplomawork.model.User;
import com.example.diplomawork.repository.TeamRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final AuthService authService;

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;


    private final TeamMapper teamMapper;

    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public void createUpdateTeam(TeamCreateUpdateRequest request) {
        User currentUser = authService.getCurrentUser();
        Team team = Team.builder()
                .id(request.getTeamId() != null ? request.getTeamId() : null)
                .name(request.getName())
                .creator(currentUser)
                .presentationURL(request.getPresentationURL())
                .confirmed(false)
                .build();

        teamRepository.save(team);
        logger.debug(" Team involved " + team.getName());
    }


    public TeamInfoWithMemberDto getTeam() {
        User currentUser = authService.getCurrentUser();
        Team team = currentUser.getTeam();
        logger.debug("Team Info for team: " + team.getName());
        return TeamInfoWithMemberDto.builder()
                .team(teamMapper.entity2dto(team))
                .member(userMapper.entity2dto(currentUser))
                .build();
    }

    public List<UserGradeDto> getGrades(){
        User currentUser = authService.getCurrentUser();
        List<UserGradeDto> grades = new ArrayList<>();
        currentUser.getGrades().forEach(userGrade -> {
            grades.add(UserGradeDto.builder()
                    .grade(userGrade.getFinalGrade())
                    .stage(userGrade.getDefence().getStage().getName())
                    .build());
        });
        logger.debug(" Get grades for user: " + currentUser.getUsername());
        return grades;
    }



}
