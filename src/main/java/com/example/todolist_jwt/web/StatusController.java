package com.example.todolist_jwt.web;

import com.example.todolist_jwt.model.domain.Status;
import com.example.todolist_jwt.service.impl.StatusServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {
    private final StatusServiceImpl statusService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/statuses/{personId}")
    public Status createStatus(@PathVariable Integer personId,  @RequestBody Status status) {
        return statusService.create(personId, status);
    }
}
