package com.example.todolist_jwt.utils.mapper;

import com.example.todolist_jwt.model.domain.Person;
import com.example.todolist_jwt.model.dto.PersonView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
   PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

   PersonView personToPersonView(Person person);
   Person personViewToPerson(PersonView personView);

}
