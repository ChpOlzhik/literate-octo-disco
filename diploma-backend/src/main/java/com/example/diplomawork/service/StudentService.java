package com.example.diplomawork.service;

import com.example.diplomawork.mapper.GroupMapper;
import com.example.diplomawork.mapper.JoinRequestMapper;
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
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Null;
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

    private final JoinRequestMapper joinRequestMapper;

    public void createUpdateTeam(TeamCreateUpdateRequest request) {
        User currentUser = authService.getCurrentUser();
        Team team = Team.builder()
                .id(request.getTeamId() != null ? request.getTeamId() : null)
                .name(request.getName())
                .creator(currentUser)
                .confirmed(false)
                .documentURL(null)
                .build();
        teamRepository.saveAndFlush(team);
        userTeamRepository.save(UserTeam.builder()
                .team(team)
                .user(currentUser)
                .accepted(true)
                .build());
    }

    public List<UserTeamInfoByBlockDto> getTeamJoinRequests() {
        User currentUser = authService.getCurrentUser();
        Team team = teamRepository.findByCreatorId(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("Team with creator id: " + currentUser.getId() + " not found"));
        return userTeamRepository.findAllByTeamIdAndAcceptedFalse(team.getId())
                .stream().map(userTeam -> UserTeamInfoByBlockDto.builder()
                        .id(userTeam.getId())
                        .team(teamMapper.entity2dto(userTeam.getTeam()))
                        .user(userMapper.entity2dto(userTeam.getUser()))
                        .group(groupMapper.entity2dto(userTeam.getUser().getGroup()))
                        .build()).collect(Collectors.toList());
    }

    @SneakyThrows
    public void acceptTeamJoinRequest(Long requestId) {
        User currentUser = authService.getCurrentUser();
        Team team = teamRepository.findByCreatorId(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("Team with creator id: " + currentUser.getId() + " not found"));
        UserTeam userTeam = userTeamRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Request with id: " + requestId + " not found"));
        if (userTeam.getTeam().getId() != team.getId()) {
            throw new IllegalAccessException("Action is not allowed!");
        }
        userTeam.setAccepted(true);
        userTeamRepository.save(userTeam);
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

    public void createRequestToTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id: " + teamId + " not found"));
        userTeamRepository.save(UserTeam.builder()
                .user(authService.getCurrentUser())
                .team(team)
                .accepted(false)
                .build());
    }

    public List<TeamJoinRequestInfoByBlocksDto> getUserRequests() {
        User currentUser = authService.getCurrentUser();
        List<UserTeam> userTeams = userTeamRepository.findAllByUserId(currentUser.getId());
        return userTeams.stream().map(userTeam -> TeamJoinRequestInfoByBlocksDto.builder()
                .request(joinRequestMapper.entity2dto(userTeam))
                .team(teamMapper.entity2dto(userTeam.getTeam()))
                .build()).collect(Collectors.toList());
    }

    public List<TeamShortInfoDto> getAvailableTeams() {
        return teamRepository.findAllByConfirmedFalse().stream().map(teamMapper::entity2dto).collect(Collectors.toList());
    }

    public void createUpdateTeamDocumentURL(){

    }
}
