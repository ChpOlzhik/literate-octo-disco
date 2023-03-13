package com.example.diplomawork.controller;


import com.example.api.ProfileApi;
import com.example.diplomawork.model.User;
import com.example.diplomawork.service.AnnouncementService;
import com.example.diplomawork.service.AuthService;
import com.example.diplomawork.service.ProfileService;
import com.example.diplomawork.service.StorageService;
import com.example.models.AnnouncementDto;
import com.example.models.FileUploadResponse;
import com.example.models.ProfileDto;
import com.example.models.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController implements ProfileApi {

    private final StorageService storageService;

    private final ProfileService profileService;

    private final AnnouncementService announcementService;

    @Override
    public ResponseEntity<ProfileDto> getProfileInfo(){
        return ResponseEntity.ok(profileService.getUserProfile());
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAndSetProfilePhoto(MultipartFile file){
        return ResponseEntity.ok(storageService.uploadAndSetProfilePicture(file));
    }

    @Override
    public ResponseEntity<List<AnnouncementDto>> getAnnouncements(){
        return ResponseEntity.ok(announcementService.getAnnouncements());
    }

    @Override
    public ResponseEntity<Void> updateProfileInfo(ProfileUpdateRequest request){
        profileService.updateProfileInfo(request);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
