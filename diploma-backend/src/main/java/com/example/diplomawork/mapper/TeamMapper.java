package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Group;
import com.example.diplomawork.model.Subject;
import com.example.diplomawork.model.Team;
import com.example.diplomawork.model.User;
import com.example.models.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamShortInfoDto entity2dto(Team team);
    TeamCreatorDto user2creatorDto(User user);
    GroupDto entity2dto(Group group);
    SubjectDto entity2dto(Subject subject);
}
