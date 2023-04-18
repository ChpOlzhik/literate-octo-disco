package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Criteria;
import com.example.models.CriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CriteriaMapper {
    CriteriaDto entity2dto(Criteria criteria);
}
