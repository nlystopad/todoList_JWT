package com.example.todolist_jwt.service.interf;

import com.example.todolist_jwt.model.domain.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks(Integer userId);
    Task getById(Integer userId, Integer taskId);
    Task getById(Integer taskId);
    Task createTask(Task task, Integer userId, String statusName);
    Task updateStatus(Integer taskId, String statusName);
}
