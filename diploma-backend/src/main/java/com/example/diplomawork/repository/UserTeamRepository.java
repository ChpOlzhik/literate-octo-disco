package com.example.diplomawork.repository;

import com.example.diplomawork.model.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    List<UserTeam> findAllByTeamIdAndAcceptedFalse(Long teamId);

    Optional<UserTeam> findByUserIdAndAcceptedTrue(Long userId);

    List<UserTeam> findAllByTeamIdAndAcceptedTrue(Long teamId);

    List<UserTeam> findAllByUserId(Long userId);
}
