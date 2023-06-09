package com.example.diplomawork.service;

import com.example.diplomawork.mapper.*;
import com.example.diplomawork.model.*;
import com.example.diplomawork.repository.*;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecretaryService {

    private final AuthService authService;

    private final DefenceRepository defenceRepository;

    private final TeamRepository teamRepository;

    private final UserGradeRepository userGradeRepository;

    private final UserCommissionGradeRepository userCommissionGradeRepository;

    private final DefenceCommissionRepository defenceCommissionRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;;

    private final SubjectRepository subjectRepository;

    private final StageRepository stageRepository;

    private final TeamMapper teamMapper;

    private final UserMapper userMapper;

    private final DefenceMapper defenceMapper;

    private final StageMapper stageMapper;

    private final GradeMapper gradeMapper;

    private final Logger logger = LoggerFactory.getLogger(SecretaryService.class);


    public List<TeamShortInfoDto> getTeams(Boolean isConfirmed) {
        List<Team> teams = isConfirmed == null? teamRepository.findAll(): isConfirmed? teamRepository.findAllByConfirmedTrue():teamRepository.findAllByConfirmedFalse();
        return teams.stream().map(teamMapper::entity2dto).collect(Collectors.toList());
    }

    public UsersNumberDto getUsersAmount(){
        return UsersNumberDto.builder()
                .number(userRepository.countByRole(roleRepository.findByName("ROLE_STUDENT")))
                .build();
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    public void updateTeamConfirmation(Long teamId, Boolean confirmed){
        Team team = teamRepository.findById(teamId).orElseThrow(()->new EntityNotFoundException("Team with id: " + teamId + " not found !"));
        logger.debug("Setting team confirmation status: teamId - " +teamId +  " confirmed - " + confirmed);
        team.setConfirmed(confirmed);
        teamRepository.save(team);
        logger.debug("Setting team confirmation status: DONE");
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
        this.setDefenceCommission(defence, request.getCommissions());
    }

    public void updateDefence(Long defenceId, CreateDefenceRequest request){
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Team with id: " + defenceId + " not found"));
        Stage stage = stageRepository.findById(request.getStageId()).orElseThrow(() -> new EntityNotFoundException("Stage with id: " + request.getStageId() + " not found"));
        defence.setStage(stage);
        defenceRepository.saveAndFlush(defence);
        userCommissionGradeRepository.deleteAllByDefenceId(defenceId);
        this.setDefenceCommission(defence, request.getCommissions());
    }

    public void deleteDefence(Long defenceId){
        defenceRepository.deleteById(defenceId);
    }

    public TeamInfoByBlocksDto getTeamInfo(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id: " + teamId + " not found"));

        List<DefenceDto> defences = team.getTeamDefences().stream().map(defence -> DefenceDto.builder()
                .id(defence.getId())
                .defenceDate(defence.getDefenceDate())
                .stage(stageMapper.entity2dto(defence.getStage()))
                .grades(defence.getDefenceGrades().stream().map(gradeMapper::entity2cgdto).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());

        return TeamInfoByBlocksDto.builder()
                .team(teamMapper.entity2dto(team))
                .defences(defences)
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
        TeamInfoWithMemberDto teamInfo = TeamInfoWithMemberDto.builder()
                .team(teamMapper.entity2dto(team))
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

    public StudentWithGradesDto getStudentWithGrades(Long defenceId) {
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        User student = defence.getTeam().getCreator();
        List<GradeDto> grades = defence.getDefenceGrades().stream().map(gradeMapper::entity2dto).collect(Collectors.toList());

        return StudentWithGradesDto.builder()
                .id(student.getId())
                .fullName(student.getFirstName() + " " + student.getLastName())
                .grades(grades)
                .stageName(defence.getStage().getName())
                .build();
    }

    public void setFinalGrade(Long defenceId, GradeDto gradeDto) {
        Defence defence = defenceRepository.findById(defenceId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + defenceId + " not found"));
        User user = defence.getTeam().getCreator();
        List<UserCommissionGrade> grades = userCommissionGradeRepository.findAllByDefenceIdAndStudentId(defenceId, user.getId());
        Map<Long, Integer> mp = new HashMap<>();
        grades.forEach(grade -> {
            logger.info("--------------- GRADES: " + grade.getGrade());
            Integer initial = mp.getOrDefault(grade.getCommission().getId(), 0);
            mp.put(grade.getCommission().getId(), initial + grade.getGrade());
        });
        UserGrade finalGrade = userGradeRepository.findByDefenceIdAndStudentId(defenceId, user.getId());
        if (finalGrade != null) {
            finalGrade.setFirstGrade(mp.values().stream().mapToInt(Integer::intValue).sum() / mp.size());
            finalGrade.setFinalGrade(gradeDto.getGrade());
            userRepository.save(user);
        } else {
            UserGrade userGrade = UserGrade.builder()
                    .id(null)
                    .firstGrade(!grades.isEmpty() ? grades.stream().mapToInt(UserCommissionGrade::getGrade).sum() / grades.size() : null)
                    .finalGrade(gradeDto.getGrade())
                    .student(user)
                    .defence(defence)
                    .build();
            userGradeRepository.save(userGrade);
        }
    }

    public List<UserDto> getDefenceCommission(Long defenceId) {
        List<DefenceCommission> defenceCommissions = defenceCommissionRepository.findDefenceCommissionsByDefenceId(defenceId);
        return defenceCommissions.stream().map(defenceCommission -> userMapper.entity2dto(defenceCommission.getCommission())).collect(Collectors.toList());
    }

    public List<CommissionDto> getCommissions(Long subjectId) {
        Role role = roleRepository.findByName("ROLE_COMMISSION");
        List<User> commissions;
        if (subjectId != null) {
            Optional<Subject> subject = subjectRepository.findById(subjectId);
            if (subject.isPresent()) commissions = userRepository.findAllByRoleAndSubject(role, subject.get());
            else commissions = userRepository.findAllByRole(role);
        } else commissions = userRepository.findAllByRole(role);

        return commissions.stream().map(userMapper::entity2CommissionDto).collect(Collectors.toList());
    }

    private void setDefenceCommission(Defence defence, List<Long> commissions){
        for (Long commission : commissions) {
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

}
