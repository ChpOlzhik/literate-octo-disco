package com.example.diplomawork.service;

import com.example.diplomawork.mapper.*;
import com.example.diplomawork.model.*;
import com.example.diplomawork.repository.*;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final GroupRepository groupRepository;

    private final TeamRepository teamRepository;

    private final DefenceRepository defenceRepository;

    private final SubjectRepository subjectRepository;

    private final DefenceCommissionRepository defenceCommissionRepository;

    private final DefenceMapper defenceMapper;

    private final TeamMapper teamMapper;

    private final RoleMapper roleMapper;

    private final GroupMapper groupMapper;

    private final UserMapper userMapper;

    private final StageMapper stageMapper;

    private final PasswordEncoder passwordEncoder;

    private final StageRepository stageRepository;

    private final SubjectMapper subjectMapper;


    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    public UserInfoByBlocksDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
        return UserInfoByBlocksDto.builder()
                .user(userMapper.entity2dto(user))
                .role(roleMapper.entity2dto(user.getRole()))
                .build();
    }

    public void createUpdateGroup(GroupDto groupDto) {
        Group group = Group.builder()
                .id(groupDto.getId() != null ? groupDto.getId() : null)
                .nameKaz(groupDto.getNameKaz())
                .nameRus(groupDto.getNameRus())
                .build();
        groupRepository.save(group);
    }

    public GroupInfoByBlocksDto getGroupInfo(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group with id: " + groupId + " not found"));
        List<UserDto> users = group.getGroupUsers().stream().map(userMapper::entity2dto).collect(Collectors.toList());
        return GroupInfoByBlocksDto.builder()
                .group(groupMapper.entity2dto(group))
                .users(users)
                .build();
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public void createUpdateStage(StageDto stageDto) {
        Stage stage = Stage.builder()
                .id(stageDto.getId() != null ? stageDto.getId() : null)
                .name(stageDto.getName())
                .build();
        stageRepository.save(stage);
    }


    public void deleteStage(Long stageId) {
        stageRepository.deleteById(stageId);
    }

    public StageInfoByBlocksDto getStageInfo(Long stageId) {
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotFoundException("Stage with id: " + stageId + " not found"));
        List<DefenceDto> defences = stage.getStageDefences().stream().map(defenceMapper::entity2dto).collect(Collectors.toList());
        return StageInfoByBlocksDto.builder()
                .stage(stageMapper.entity2dto(stage))
                .defences(defences)
                .build();
    }

    @SneakyThrows
    public void createDefence(Long teamId, CreateDefenceRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id: " + teamId + " not found"));
        Stage stage = stageRepository.findById(request.getStageId()).orElseThrow(() -> new EntityNotFoundException("Stage with id: " + request.getStageId() + " not found"));
        if (defenceRepository.existsByTeamIdAndStageId(teamId, request.getStageId())) {
            throw new IllegalAccessException("Defence with team id: " + teamId + " and stage id: " + request.getStageId() + " already exists");
        }
        Defence defence = Defence.builder()
                .defenceDate(request.getDefenceDate())
                .stage(stage)
                .team(team)
                .build();
        defenceRepository.saveAndFlush(defence);
        for (Long commission : request.getCommissions()) {
            User user = userRepository.findById(commission).get();
            if (!user.getRole().getName().equals("ROLE_COMMISSION")) {
                throw new EntityNotFoundException("The user's role is not commission");
            }
            DefenceCommission build = DefenceCommission.builder()
                    .defence(defence)
                    .commission(user).build();
            defenceCommissionRepository.save(build);
        }
    }

    private List<TeamInfoByBlocksDto> getConfirmedTeams() {
        return teamRepository.findAllByConfirmedTrue().stream().map(team -> TeamInfoByBlocksDto.builder()
                .team(teamMapper.entity2dto(team))
                .creator(userMapper.entity2dto(team.getCreator()))
                .build()).collect(Collectors.toList());
    }


    private List<UserInfoByBlocksDto> getUsers() {
        return userRepository.findAll().stream().map(user -> UserInfoByBlocksDto.builder()
                .user(userMapper.entity2dto(user))
                .role(roleMapper.entity2dto(user.getRole()))
                .build()).collect(Collectors.toList());
    }

    private List<GroupDto> getGroups() {
        return groupRepository.findAll().stream().map(groupMapper::entity2dto).collect(Collectors.toList());
    }

    public List<StageDto> getStages() {
        return stageRepository.findAll().stream().map(stageMapper::entity2dto).collect(Collectors.toList());
    }

    public List<UserDto> getCommissions() {
        Role role = roleRepository.findByName("ROLE_COMMISSION");
        List<User> commissions = userRepository.findAllByRole(role);
        return commissions.stream().map(userMapper::entity2dto).collect(Collectors.toList());
    }

    public void createUpdateCommissionMember(CreateCommissionMemberRequest request) {
        User user = User.builder()
                .id(request.getId() != null ? request.getId() : null)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.findByName("ROLE_COMMISSION"))
                .build();
        userRepository.save(user);
    }

    public void createUpdateSubject(SubjectDto subjectDto) {
        Subject subject = Subject.builder()
                .id(subjectDto.getId() != null ? subjectDto.getId() : null)
                .nameKaz(subjectDto.getNameKaz())
                .nameRus(subjectDto.getNameRus())
                .build();
        subjectRepository.save(subject);
    }

    public SubjectInfoByBlocksDto getSubjectInfo(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("Subject with id: " + subjectId + " not found"));
        List<UserDto> users = subject.getSubjectUsers().stream().map(userMapper::entity2dto).collect(Collectors.toList());
        return SubjectInfoByBlocksDto.builder()
                .subject(subjectMapper.entity2dto(subject))
                .users(users)
                .build();
    }

    public void deleteSubject(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}
