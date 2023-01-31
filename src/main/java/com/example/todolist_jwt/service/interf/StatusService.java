package com.example.todolist_jwt.service.interf;

import com.example.todolist_jwt.model.domain.Status;

public interface StatusService {
    Status create(Integer userId, Status status);
    Status getStatusByName(String name);

    Status removeTaskById(Integer taskId, Status status);
    Status addTaskById(Integer taskId, Status status);
    Status findById(Integer statusId);
    void addTask(Integer statusId, Integer taskId);
}
