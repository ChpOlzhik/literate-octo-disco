package com.example.diplomawork.repository;

import com.example.diplomawork.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{
}
