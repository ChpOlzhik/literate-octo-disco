package com.example.diplomawork.service;

import com.example.diplomawork.model.Announcement;
import com.example.diplomawork.repository.AnnouncementRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.models.AnnouncementCreateUpdateRequest;
import com.example.models.AnnouncementDto;
import com.example.models.CreateAnnouncementResponse;
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
    private final UserRepository userRepository;

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
                    .creator(announcement.getCreator().getId())
                    .build());
        });
        return announcementDtos;
    }

    public AnnouncementDto getAnnouncement(Long announcementId){
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(() -> new EntityNotFoundException("Defence with id: " + announcementId + " not found"));
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .text(announcement.getText())
                .content(announcement.getContent())
                .filename(announcement.getFilename())
                .date(announcement.getDate())
                .creator(announcement.getCreator().getId())
                .build();
    }

    public CreateAnnouncementResponse createUpdateAnnouncement(AnnouncementCreateUpdateRequest request){
        Announcement newAnnouncement = Announcement.builder()
                .id(request.getId() == null? request.getId() : null)
                .title(request.getTitle())
                .text(request.getText())
                .content(request.getText())
                .filename(request.getFilename())
                .creator(userRepository.getById(request.getCreator()))
                .build();

        announcementRepository.save(newAnnouncement);
        return CreateAnnouncementResponse.builder().announcementId(newAnnouncement.getId()).build();
    }

    public void deleteAnnouncement(Long announcementId){
        announcementRepository.deleteById(announcementId);
    }

}
