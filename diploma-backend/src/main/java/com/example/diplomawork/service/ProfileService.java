package com.example.diplomawork.service;

import com.example.diplomawork.mapper.GroupMapper;
import com.example.diplomawork.mapper.SubjectMapper;
import com.example.diplomawork.repository.GroupRepository;
import com.example.diplomawork.repository.SubjectRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.model.*;
import com.example.models.ProfileDto;
import com.example.models.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final AuthService authService;

    private final GroupRepository groupRepository;

    private final SubjectRepository subjectRepository;

    private final UserRepository userRepository;

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

    public void updateProfileInfo(ProfileUpdateRequest request){
        User cu = authService.getCurrentUser();
        cu.setFirstName(request.getFirstName() == null ? cu.getFirstName(): request.getFirstName());
        cu.setLastName(request.getLastName() == null ? cu.getLastName(): request.getLastName());
        cu.setMiddleName(request.getMiddleName() == null ? cu.getMiddleName(): request.getMiddleName());
        cu.setBirthDate(request.getBirthDate() == null ? cu.getBirthDate(): request.getBirthDate());
        userRepository.saveAndFlush(cu);
        cu.setGroup(request.getGroup() == null? cu.getGroup() : groupRepository.findById(request.getGroup()).orElseThrow(() -> new EntityNotFoundException("Group with id: "+ request.getGroup() + " not found;")));
        cu.setSubject(request.getSubject() == null? cu.getSubject() : subjectRepository.findById(request.getSubject()).orElseThrow(() -> new EntityNotFoundException("Subject with id: "+ request.getSubject() + " not found;")));
        userRepository.save(cu);
    }
}
