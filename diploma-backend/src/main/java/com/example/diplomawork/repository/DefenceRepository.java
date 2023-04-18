package com.example.diplomawork.repository;

import com.example.diplomawork.model.Defence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DefenceRepository extends JpaRepository<Defence, Long> {
    boolean existsByTeamIdAndStageId(Long teamId, Long stageId);

    List<Defence> findAllByStageId(Long stageId);

    Defence findByTeamIdAndStageName(Long teamId, String stageName);
}
