package com.example.diplomawork.repository;

import com.example.diplomawork.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByConfirmedFalse();

    List<Team> findAllByConfirmedTrue();

    Optional<Team> findByCreatorId(Long id);

    Team findTeamByName(String name);
}
