package com.example.diplomawork.repository;

import com.example.diplomawork.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{

    List<Announcement> findAllByOrderByDateAsc();
}
