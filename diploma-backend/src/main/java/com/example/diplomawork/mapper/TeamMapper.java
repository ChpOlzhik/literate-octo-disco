package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Subject;
import com.example.diplomawork.model.Team;
import com.example.diplomawork.model.User;
import com.example.models.SubjectDto;
import com.example.models.TeamCreateUpdateRequest;
import com.example.models.TeamCreatorDto;
import com.example.models.TeamShortInfoDto;
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

    SubjectDto entity2dto(Subject subject);
}
