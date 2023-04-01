package com.example.diplomawork.mapper;

import com.example.diplomawork.model.User;
import com.example.diplomawork.model.UserCommissionGrade;
import com.example.models.CommissionDto;
import com.example.models.CommissionGradeDto;
import com.example.models.GradeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(target = "grade", source = "grade")
    GradeDto entity2dto(UserCommissionGrade grade);

    @Mapping(target = "grade", source = "grade")
    @Mapping(target = "commission", source = "commission")
    CommissionGradeDto entity2cgdto(UserCommissionGrade grade);

    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "id", ignore = true)
    CommissionDto commission2dto(User commission);
}
