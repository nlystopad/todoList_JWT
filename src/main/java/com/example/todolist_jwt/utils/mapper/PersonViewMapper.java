package com.example.todolist_jwt.utils.mapper;

import com.example.todolist_jwt.model.domain.Person;
import com.example.todolist_jwt.model.dto.PersonView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonViewMapper {
    PersonViewMapper INSTANCE = Mappers.getMapper(PersonViewMapper.class);

    PersonView PersonToPersonView(Person person);
}
