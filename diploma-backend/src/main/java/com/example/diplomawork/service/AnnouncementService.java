package com.example.diplomawork.service;

import com.example.diplomawork.model.Announcement;
import com.example.diplomawork.repository.AnnouncementRepository;
import com.example.models.AnnouncementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<AnnouncementDto> getAnnouncements(){
        List<Announcement> announcements = announcementRepository.findAll();
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        announcements.forEach(announcement -> {
            announcementDtos.add(AnnouncementDto.builder()
                    .id(announcement.getId())
                    .title(announcement.getTitle())
                    .text(announcement.getText())
                    .content(announcement.getContent())
                    .filename(announcement.getFilename())
                    .date(announcement.getDate())
                    .build());
        });
        return announcementDtos;
    }

    public AnnouncementDto getAnnouncement(Long announcementId){
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + announcementId + " not found"));;
        AnnouncementDto announcementDto = AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .text(announcement.getText())
                .content(announcement.getContent())
                .filename(announcement.getFilename())
                .date(announcement.getDate())
                .build();
        return announcementDto;
    }

}
