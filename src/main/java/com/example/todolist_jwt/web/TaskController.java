package com.example.todolist_jwt.web;

import com.example.todolist_jwt.model.domain.Task;
import com.example.todolist_jwt.service.impl.TaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    private final TaskServiceImpl taskService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tasks/{personId}")
    public List<Task> getAllTasksByPersonId(@PathVariable Integer personId) {
        return taskService.getAllTasks(personId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/tasks/{personId}", params = {"statusName"})
    public Task createTask(@RequestBody Task task,@PathVariable Integer personId, String statusName) {
        return taskService.createTask(task, personId, statusName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tasks/{personId}/{taskId}")
    public Task getTaskById(@PathVariable Integer personId, @PathVariable Integer taskId) {
        return taskService.getById(personId, taskId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/tasks/{taskId}", params = {"statusName"})
    public Task updateStatus(@PathVariable Integer taskId, String statusName) {
        return taskService.updateStatus(taskId, statusName);
    }

    //calendar pagination
}
