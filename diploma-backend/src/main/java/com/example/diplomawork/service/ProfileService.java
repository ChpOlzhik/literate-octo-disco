package com.example.diplomawork.service;

import com.example.diplomawork.mapper.GroupMapper;
import com.example.diplomawork.mapper.SubjectMapper;
import com.example.diplomawork.repository.CategoryRepository;
import com.example.diplomawork.repository.GroupRepository;
import com.example.diplomawork.repository.SubjectRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.model.*;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    private final CategoryRepository categoryRepository;


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
        cu.setFirstName(request.getFirstName());
        cu.setLastName(request.getLastName());
        cu.setMiddleName(request.getMiddleName());
        cu.setBirthDate(request.getBirthDate());
        cu.setIsKazakhProficient(request.getIsKazakhProficient());
        cu.setEnglishProficiency(request.getEnglishProficiency());
        cu.setPedagogicalExperience(request.getPedagogicalExperience());
        cu.setPedagogicalExperienceCurrent(request.getPedagogicalExperienceCurrent());
        cu.setGroup(groupRepository.findById(request.getGroup()).orElseThrow(() -> new EntityNotFoundException("Group with id: "+ request.getGroup() + " not found;")));
        cu.setSubject(subjectRepository.findById(request.getSubject()).orElseThrow(() -> new EntityNotFoundException("Subject with id: "+ request.getSubject() + " not found;")));
        cu.setCategory(categoryRepository.findById(request.getCategory()).orElseThrow(() -> new EntityNotFoundException("Category with id: "+ request.getCategory() + " not found;")));
        userRepository.save(cu);
    }

    public List<GroupDto> getGroups(){
        List<GroupDto> groups = new ArrayList<>();
        groupRepository.findAll().forEach(group -> {
            groups.add(GroupDto.builder()
                            .id(group.getId())
                            .nameKaz(group.getNameKaz())
                            .nameRus(group.getNameRus())
                            .build());
        });
        return groups;
    }

    public List<SubjectDto> getSubjects(){
        List<SubjectDto> subjects = new ArrayList<>();
        subjectRepository.findAll().forEach(subject -> {
            subjects.add(SubjectDto.builder()
                    .id(subject.getId())
                    .nameKaz(subject.getNameKaz())
                    .nameRus(subject.getNameRus())
                    .build());
        });
        return subjects;
    }

    public List<CategoryDto> getCategories(){
        List<CategoryDto> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            categories.add(CategoryDto.builder()
                    .id(category.getId())
                    .nameKaz(category.getNameKaz())
                    .nameRus(category.getNameRus())
                    .build());
        });
        return categories;
    }
}
