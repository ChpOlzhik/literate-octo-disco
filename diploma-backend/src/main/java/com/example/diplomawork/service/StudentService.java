package com.example.diplomawork.service;

import com.example.diplomawork.mapper.GroupMapper;
import com.example.diplomawork.mapper.TeamMapper;
import com.example.diplomawork.mapper.UserMapper;
import com.example.diplomawork.model.Team;
import com.example.diplomawork.model.User;
import com.example.diplomawork.model.UserTeam;
import com.example.diplomawork.repository.TeamRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.repository.UserTeamRepository;
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
public class StudentService {

    private final AuthService authService;

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    private final UserTeamRepository userTeamRepository;

    private final TeamMapper teamMapper;

    private final UserMapper userMapper;

    private final GroupMapper groupMapper;


    public void createUpdateTeam(TeamCreateUpdateRequest request) {
        User currentUser = authService.getCurrentUser();
        Team team = Team.builder()
                .id(request.getTeamId() != null ? request.getTeamId() : null)
                .name(request.getName())
                .creator(currentUser)
                .presentationURL(request.getPresentationURL())
                .confirmed(false)
                .build();
        teamRepository.saveAndFlush(team);
        userTeamRepository.save(UserTeam.builder()
                .team(team)
                .user(currentUser)
                .accepted(true)
                .build());
    }


    public TeamInfoWithMembersDto getTeam() {
        User currentUser = authService.getCurrentUser();
        UserTeam userTeamSet = userTeamRepository.findByUserIdAndAcceptedTrue(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("Team with member id: " + currentUser.getId() + " not found"));
        Team team = userTeamSet.getTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByTeamIdAndAcceptedTrue(team.getId());
        return TeamInfoWithMembersDto.builder()
                .team(teamMapper.entity2dto(team))
                .members(userTeams.stream().map(userTeam -> userMapper.entity2dto(userTeam.getUser())).collect(Collectors.toList()))
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
        return grades;
    }



}
