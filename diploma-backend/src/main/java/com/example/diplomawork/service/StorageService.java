package com.example.diplomawork.service;

import com.example.diplomawork.model.Announcement;
import com.example.diplomawork.model.User;
import com.example.diplomawork.model.UserTeam;
import com.example.diplomawork.repository.AnnouncementRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.repository.UserTeamRepository;
import com.example.models.FileUploadResponse;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;


@Service
public class StorageService {

    private final Storage storage;
    private final String bucketName;

    private final AuthService authService;

    private final AnnouncementRepository announcementRepository;

    private final UserRepository userRepository;

    private final UserTeamRepository userTeamRepository;

    @Autowired
    public StorageService(AuthService authService, AnnouncementRepository announcementRepository, UserRepository userRepository, UserTeamRepository userTeamRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.userTeamRepository = userTeamRepository;
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.bucketName = "almatyustazy-profile-bucket";
        this.announcementRepository = announcementRepository;
    }

    public FileUploadResponse uploadAndSetAnnouncementFile(MultipartFile file, Long announcementId){
        String filePath = "announcements/announcement-" + announcementId.toString();
        Announcement announcement = announcementRepository.findById(announcementId).get();
        String announcementFileURL = uploadFile(file, this.bucketName, filePath);
        announcement.setFilename(announcementFileURL);
        announcementRepository.save(announcement);
        return FileUploadResponse.builder().fileUrl(announcementFileURL).build();
    }

    public FileUploadResponse uploadAndSetProfilePicture(MultipartFile file){
        User currentUser = authService.getCurrentUser();
        String filePath = "profile_images/" + currentUser.getId().toString();
        String profilePhotoURL = uploadFile(file, this.bucketName, filePath);
        currentUser.setProfilePhoto(profilePhotoURL);
        userRepository.save(currentUser);
        return FileUploadResponse.builder().fileUrl(profilePhotoURL).build();
    }

    public FileUploadResponse uploadParticipantPresentation(MultipartFile file){
        User currentUser = authService.getCurrentUser();
        UserTeam userTeamSet = userTeamRepository.findByUserIdAndAcceptedTrue(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("Team with member id: " + currentUser.getId().toString() + " not found"));
        String filePath = "participant_presentations/" + userTeamSet.getId().toString();
        String presentationURL = uploadFile(file, this.bucketName, filePath);
        userTeamSet.setPresentationURL(presentationURL);
        userTeamRepository.saveAndFlush(userTeamSet);
        return FileUploadResponse.builder().fileUrl(presentationURL).build();
    }

    private String uploadFile(MultipartFile file, String bucketName, String filePath){
        try{
            BlobId blobId = BlobId.of(bucketName, filePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            Blob blob = storage.create(blobInfo, file.getBytes());
            return blob.getMediaLink();
        } catch (Exception e){
            return "";
        }
    }

}
