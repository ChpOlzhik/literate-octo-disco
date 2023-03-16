package com.example.diplomawork.service;

import com.example.diplomawork.controller.StudentController;
import com.example.diplomawork.model.*;
import com.example.diplomawork.repository.AnnouncementRepository;
import com.example.diplomawork.repository.TeamRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.repository.UserTeamRepository;
import com.example.models.FileUploadResponse;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;


@Service
public class StorageService {

    private final Storage storage;
    private final String bucketName;

    private final AuthService authService;

    private final AnnouncementRepository announcementRepository;

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StorageService(AuthService authService, AnnouncementRepository announcementRepository, UserRepository userRepository, UserTeamRepository userTeamRepository, com.example.diplomawork.repository.TeamRepository teamRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.userTeamRepository = userTeamRepository;
        this.teamRepository = teamRepository;
        this.announcementRepository = announcementRepository;
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.bucketName = "almatyustazy-profile-bucket";
    }

    public FileUploadResponse uploadAndSetAnnouncementFile(MultipartFile file, Long announcementId){
        try {
            String filePath = "announcements/announcement-" + announcementId.toString();
            logger.debug("Upload file for Announcement with id: " + announcementId);
            Announcement announcement = announcementRepository.findById(announcementId).get();
            String announcementFileURL = uploadFile(file, this.bucketName, filePath);
            announcement.setFilename(announcementFileURL);
            announcementRepository.save(announcement);
            return FileUploadResponse.builder().fileUrl(announcementFileURL).build();
        } catch (NoSuchElementException e){
            logger.debug("Upload announcement file announcement with id " + announcementId + "not found !");
            logger.debug(e.toString());
        } catch (Exception e){
            logger.error("Upload announcement file: " + e);
        }
        return  FileUploadResponse.builder().fileUrl("").build();
    }

    public FileUploadResponse uploadAndSetProfilePicture(MultipartFile file){
        try {
            User currentUser = authService.getCurrentUser();
            String filePath = "profile_images/" + currentUser.getId().toString();
            String profilePhotoURL = uploadFile(file, this.bucketName, filePath);
            currentUser.setProfilePhoto(profilePhotoURL);
            userRepository.save(currentUser);
            return FileUploadResponse.builder().fileUrl(profilePhotoURL).build();
        } catch (Exception e){
            logger.error("Upload profile photo: " + e);
        }
        return FileUploadResponse.builder().fileUrl("").build();
    }

    public FileUploadResponse uploadParticipantPresentation(MultipartFile file){
        try{
            User currentUser = authService.getCurrentUser();
            UserTeam userTeamSet = userTeamRepository.findByUserIdAndAcceptedTrue(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("Team with member id: " + currentUser.getId().toString() + " not found"));
            Team team = userTeamSet.getTeam();
            String filePath = "participant_presentations/" + team.getName();
            logger.debug("Presentation upload request | Team name: " + team.getName());
            String presentationURL = uploadFile(file, this.bucketName, filePath);
            logger.debug("Team name: " + team.getName() + "presentation url is set: " + !presentationURL.equals(""));
            team.setPresentationURL(presentationURL);
            teamRepository.saveAndFlush(team);
            return FileUploadResponse.builder().fileUrl(presentationURL).build();
        } catch (Exception e){
            logger.error("Upload presentation: " + e);
        }
        return FileUploadResponse.builder().fileUrl("").build();
    }

    private String uploadFile(MultipartFile file, String bucketName, String filePath){
        try{
            BlobId blobId = BlobId.of(bucketName, filePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            logger.info("Writing files with blob info: " + blobInfo.getName());
            Blob blob = storage.create(blobInfo, file.getBytes());
            return blob.getMediaLink();
        } catch (Exception e){
            logger.error("Upload file error: " + e);
            return "";
        }
    }

}
