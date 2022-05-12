package com.example.diplomawork.mapper;

import com.example.diplomawork.model.Defence;
import com.example.diplomawork.model.Question;
import com.example.models.QuestionCreateUpdateRequest;
import com.example.models.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(target = "questionerId", expression = "java(question.getQuestioner().getId())")
    @Mapping(target = "defenceId", expression = "java(question.getDefence().getId())")
    QuestionDto entity2dto(Question question);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "defence", source = "defence")
    Question request2entity(QuestionCreateUpdateRequest request, Defence defence);
}
