package com.example.todolist_jwt.service.impl;

import com.example.todolist_jwt.model.domain.Task;
import com.example.todolist_jwt.repository.TaskRepository;
import com.example.todolist_jwt.service.interf.TaskService;
import com.example.todolist_jwt.utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.lang.String.format;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PersonServiceImpl personService;
    private final StatusServiceImpl statusService;


    @Override
    public List<Task> getAllTasks(Integer userId) {
        return taskRepository.findAllByPersonId(userId);
    }

    @Override
    public Task getById(Integer userId, Integer taskId) {
        return taskRepository.findById(userId, taskId);
    }

    @Override
    public Task getById(Integer taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException(format("Task with id %d was not found", taskId))
        );
    }

    @Override
    public Task createTask(Task task, Integer userId, String statusName) {
        // check user exist
        personService.findById(userId);
        // check status exist
        statusService.checkStatusExist(statusName);
        task.setStatus(statusService.getStatusByName(statusName));
        task.setPerson(personService.findById(userId));
        // add task in db
        Task save = taskRepository.save(task);
        // add task to user list
        personService.addTask(userId, save.getId());
        //add task to status list
        statusService.addTask(userId, save.getId());
        return save;
    }


    @Override
    public Task updateStatus(Integer taskId, String statusName) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException(format("Task with id %d was not found", taskId))
        );
        statusService.checkStatusExist(statusName);
        statusService.removeTaskById(taskId, task.getStatus());
        statusService.addTaskById(taskId, statusService.getStatusByName(statusName));
        return taskRepository.findById(taskId)
                .map(entity -> {
                    entity.setStatus(statusService.getStatusByName(statusName));
                    return taskRepository.save(entity);
                }).orElseThrow(
                        () -> new NotFoundException(format("Task with id %d was not found", taskId))
                );

    }
}
