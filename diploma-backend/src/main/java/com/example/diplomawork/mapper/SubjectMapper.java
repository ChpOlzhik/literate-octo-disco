package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Subject;
import com.example.models.SubjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectDto entity2dto(Subject subject);
}
