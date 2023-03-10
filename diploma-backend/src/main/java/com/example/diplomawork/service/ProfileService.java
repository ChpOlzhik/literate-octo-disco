package com.example.diplomawork.service;

import com.example.diplomawork.mapper.GroupMapper;
import com.example.diplomawork.mapper.SubjectMapper;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.model.*;
import com.example.models.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final AuthService authService;

    private final GroupMapper groupMapper;
    private final SubjectMapper subjectMapper;


    public ProfileDto getUserProfile(){
        User currentUser = authService.getCurrentUser();
        return ProfileDto.builder()
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .middleName(currentUser.getMiddleName())
                .birthDate(currentUser.getBirthDate())
                .profilePhoto(currentUser.getProfilePhoto())
                .group(groupMapper.entity2dto(currentUser.getGroup()))
                .subject(subjectMapper.entity2dto(currentUser.getSubject()))
                .build();
    }
}
