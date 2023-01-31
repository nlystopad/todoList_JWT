package com.example.todolist_jwt.service.impl;

import com.example.todolist_jwt.model.domain.Person;
import com.example.todolist_jwt.repository.PersonRepository;
import com.example.todolist_jwt.repository.StatusRepository;
import com.example.todolist_jwt.repository.TaskRepository;
import com.example.todolist_jwt.service.interf.PersonService;
import com.example.todolist_jwt.utils.exceptions.AlreadyExistException;
import com.example.todolist_jwt.utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static java.lang.String.format;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    private final StatusRepository statusRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Person create(Person person) {
        checkAlreadyExistByEmail(person.getEmail());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person login(String email) {
        Person byEmail = personRepository.findByEmail(email);
        if (byEmail == null) {
            throw new NotFoundException(format("User with email %s was not found", email));
        }
        return byEmail;
    }

    @Override
    @Transactional
    public Person update(Person person) {
        return personRepository.findById(person.getId())
                .map(entity -> {
                    entity.setEmail(person.getEmail());
                    entity.setPassword(person.getPassword());
                    return personRepository.save(entity);
                }).orElseThrow(()-> new NotFoundException(format("User with id %d was not found", person.getId())));

    }

    @Override
    @Transactional
    public Person findById(Integer id) {
        return personRepository.findById(id).orElseThrow(
                () -> new NotFoundException(format("User with id %d was not found", id))
        );
    }

    @Transactional
    public void addStatus(Integer userId, Integer statusId) {
        personRepository.findById(userId)
                .map(entity -> {
                    entity.getStatuses()
                            .add(statusRepository.findById(statusId).orElseThrow(
                                    () -> new NotFoundException(format("Status with id %d was not found", statusId))
                            ));
                    return personRepository.save(entity);
                });
    }

    @Override
    @Transactional
    public void addTask(Integer userId, Integer taskId) {
        personRepository.findById(userId)
                .map(entity -> {
                    entity.getTasks()
                            .add(taskRepository.findById(taskId).orElseThrow(
                                    () -> new NotFoundException(format("Task with id %d was not found", taskId))
                            ));
                    return personRepository.save(entity);
                });
    }

    /**
     * Technical method that checks if user with such email already exist in database
     *
     * @param email - email of user to check
     */
    public void checkAlreadyExistByEmail(String email) {
        Person byEmail = personRepository.findByEmail(email);
        if (byEmail != null) {
            throw new AlreadyExistException(format("User with email %s already exists", email));
        }
    }
}
