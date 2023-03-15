package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Group;
import com.example.models.GroupDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDto entity2dto(Group group);
}
