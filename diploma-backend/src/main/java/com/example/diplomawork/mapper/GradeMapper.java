package com.example.diplomawork.mapper;

import com.example.diplomawork.model.UserCommissionGrade;
import com.example.models.GradeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(target = "grade", source = "grade")
    GradeDto entity2dto(UserCommissionGrade grade);
}
