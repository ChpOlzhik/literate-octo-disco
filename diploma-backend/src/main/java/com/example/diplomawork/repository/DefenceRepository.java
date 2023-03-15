package com.example.diplomawork.repository;

import com.example.diplomawork.model.Defence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DefenceRepository extends JpaRepository<Defence, Long> {
    boolean existsByTeamIdAndStageId(Long teamId, Long stageId);

    Defence findByTeamIdAndStageName(Long teamId, String stageName);
}
