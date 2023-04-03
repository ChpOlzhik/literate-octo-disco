package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Group;
import com.example.diplomawork.model.Subject;
import com.example.diplomawork.model.Team;
import com.example.diplomawork.model.User;
import com.example.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "id", source = "teamId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "presentationURL", source = "presentationURL")
    @Mapping(target = "applicationFormURL", source = "applicationFormURL")
    Team request2entity(TeamCreateUpdateRequest request);

    TeamShortInfoDto entity2dto(Team team);

    TeamCreatorDto user2creatorDto(User user);

    GroupDto entity2dto(Group group);
    SubjectDto entity2dto(Subject subject);
}
