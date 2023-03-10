package com.example.diplomawork.controller;


import com.example.api.ProfileApi;
import com.example.diplomawork.model.User;
import com.example.diplomawork.service.AuthService;
import com.example.diplomawork.service.ProfileService;
import com.example.diplomawork.service.StorageService;
import com.example.models.FileUploadResponse;
import com.example.models.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ProfileController implements ProfileApi {

    private final StorageService storageService;

    private final ProfileService profileService;

    @Override
    public ResponseEntity<ProfileDto> getProfileInfo(){
        return ResponseEntity.ok(profileService.getUserProfile());
    }

    @Override
    public ResponseEntity<FileUploadResponse> uploadAndSetProfilePhoto(MultipartFile file){
        return ResponseEntity.ok(storageService.uploadAndSetProfilePicture(file));
    }


}
