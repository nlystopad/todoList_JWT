package com.example.todolist_jwt.mapper;

import com.example.todolist_jwt.model.domain.Person;
import com.example.todolist_jwt.model.dto.PersonView;
import com.example.todolist_jwt.utils.mapper.PersonMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class PersonMapperTest {

    @Test
    public void shouldMapPersonToView() {
        // given
        Person person = new Person("1234@gmail", "1234");
        person.setId(3);

        // when
        PersonView personView = PersonMapper.INSTANCE.personToPersonView(person);

        // then
        assertThat(personView).isNotNull();
        assertThat(personView.getEmail()).isEqualTo("1234@gmail");
        assertThat(personView.getId()).isEqualTo(3);
    }
}
