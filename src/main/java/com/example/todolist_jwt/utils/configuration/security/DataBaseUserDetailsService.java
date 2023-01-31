package com.example.todolist_jwt.utils.configuration.security;

import com.example.todolist_jwt.model.domain.Person;
import com.example.todolist_jwt.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DataBaseUserDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;
//    private final UserDetailsMapper userDetailsMapper;

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByEmail(username);
    }
}
