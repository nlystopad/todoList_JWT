package com.example.todolist_jwt.utils.mapper;

import com.example.todolist_jwt.model.domain.Person;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {
    public UserDetails toUserDetails(Person person) {
        return User.withUsername(person.getEmail())
                .password(person.getPassword())
                .authorities(person.getAuthorities())
                .build();
    }
}
