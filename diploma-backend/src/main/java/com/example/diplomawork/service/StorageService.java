package com.example.diplomawork.service;

import com.example.diplomawork.model.Announcement;
import com.example.diplomawork.repository.AnnouncementRepository;
import com.example.models.FileUploadResponse;
import com.google.cloud.storage.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class StorageService {

    private final Storage storage;
    private final String bucketName;

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public StorageService(AnnouncementRepository announcementRepository) {
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
