package com.example.diplomawork.mapper;

import com.example.diplomawork.model.User;
import com.example.models.CommissionDto;
import com.example.models.ProfileDto;
import com.example.models.RegisterRequest;
import com.example.models.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "subject", ignore = true)
    User dto2entity(RegisterRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "subject", source = "subject")
    CommissionDto entity2CommissionDto(User user);
    UserDto entity2dto(User user);

    ProfileDto entity2profiledto(User user);
}
