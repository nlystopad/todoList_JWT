package com.example.todolist_jwt.service.interf;

import com.example.todolist_jwt.model.domain.Person;

public interface PersonService {
    Person create(Person person);
    Person login(String email);
    Person update(Person person);
    Person findById(Integer id);
    void addStatus(Integer userId, Integer statusId);
    void addTask(Integer userId, Integer taskId);
}
