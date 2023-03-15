package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Team;
import com.example.models.TeamCreateUpdateRequest;
import com.example.models.TeamShortInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "id", source = "teamId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "presentationURL", source = "presentationURL")
    Team request2entity(TeamCreateUpdateRequest request);

    TeamShortInfoDto entity2dto(Team team);
}
