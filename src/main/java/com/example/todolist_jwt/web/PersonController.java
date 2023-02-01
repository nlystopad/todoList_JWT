package com.example.todolist_jwt.web;

import com.example.todolist_jwt.model.domain.Person;
import com.example.todolist_jwt.model.dto.AuthRequest;
import com.example.todolist_jwt.model.dto.PersonView;
import com.example.todolist_jwt.service.impl.PersonServiceImpl;
import com.example.todolist_jwt.utils.configuration.security.JwtTokenUtil;
import com.example.todolist_jwt.utils.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PersonController {
    private final PersonServiceImpl personService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PersonMapper personMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Person createPerson(@RequestBody Person person) {
        return personService.create(person);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/login")
    public ResponseEntity<PersonView> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            Person person = (Person) authentication.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(person))
                    .body(personMapper.personToPersonView(person));
        } catch (BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }
}
