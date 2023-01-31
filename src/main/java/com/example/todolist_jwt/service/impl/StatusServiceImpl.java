package com.example.todolist_jwt.service.impl;

import com.example.todolist_jwt.model.domain.Person;
import com.example.todolist_jwt.model.domain.Status;
import com.example.todolist_jwt.repository.StatusRepository;
import com.example.todolist_jwt.repository.TaskRepository;
import com.example.todolist_jwt.service.interf.StatusService;
import com.example.todolist_jwt.utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    private final PersonServiceImpl personService;

    private final TaskRepository taskRepository;


    @Override
    public Status create(Integer personId, Status status) {
        Person person = personService.findById(personId);
        if (checkStatusExist(status.getName())) {
            Status byName = statusRepository.findByName(status.getName());
            // update set of statuses in user and set of users in status
            personService.addStatus(personId, byName.getId());
            statusRepository.findById(byName.getId())
                    .map(entity -> {
                        entity.getPeople().add(person);
                        return statusRepository.save(entity);
                    });
            return byName;
        } else {
            //create status and update set of statuses in user
            Status save = statusRepository.save(status);
            personService.addStatus(personId, save.getId());
            return save;
        }
    }

    @Override
    public Status getStatusByName(String name) {
        checkStatusExist(name);
        return statusRepository.findByName(name);
    }

    @Override
    public Status removeTaskById(Integer taskId, Status status) {
        checkStatusExist(status.getName());
        Status byName = statusRepository.findByName(status.getName());
        return statusRepository.findById(byName.getId())
                .map(entity -> {
                    entity.getTasks().remove(taskRepository.findById(taskId).orElseThrow(
                            () -> new NotFoundException(format("Task with id %d was not found", taskId))
                    ));
                    return statusRepository.save(entity);
                }).orElseThrow(
                        ()-> new NotFoundException(format("Status with id %d was not found", byName.getId()))
                );
    }

    @Override
    public Status addTaskById(Integer taskId, Status status) {
        // if status given in request body doesn't contain id, we're using status name to find status in DB
        if (status.getId() == null) {
            checkStatusExist(status.getName());
            Status byName = statusRepository.findByName(status.getName());
            return statusRepository.findById(byName.getId())
                    .map(entity -> {
                        entity.getTasks().add(taskRepository.findById(taskId).orElseThrow(
                                () -> new NotFoundException(format("Task with id %d was not found", taskId))
                        ));
                        return statusRepository.save(entity);
                    }).orElseThrow(); // exception (name not found) is already thrown in checkStatusExist(), but map() works only with byId()
        }
        // if status given in request body contains id, we're using it to find status in DB
        else {
            return statusRepository.findById(status.getId())
                    .map(entity -> {
                        entity.getTasks().add(taskRepository.findById(taskId).orElseThrow(
                                () -> new NotFoundException(format("Task with id %d was not found", taskId))
                        ));
                        return statusRepository.save(entity);
                    }).orElseThrow(
                            () -> new NotFoundException(format("Status with id %d was not found", status.getId()))
                    );
        }
    }

    @Override
    public Status findById(Integer statusId) {
        return statusRepository.findById(statusId).orElseThrow(
                () -> new NotFoundException(format("Status with id %d was not found", statusId))
        );
    }

    @Override
    public void addTask(Integer statusId, Integer taskId) {
        statusRepository.findById(statusId)
                .map(entity -> {
                    entity.getTasks()
                            .add(taskRepository.findById(taskId).orElseThrow(
                                    () -> new NotFoundException(format("Task with id %d was not found", taskId))
                            ));
                    return statusRepository.save(entity);
                }).orElseThrow(
                        () -> new NotFoundException(format("Status with id %d was not found", statusId))
                );
    }


    /**
     * technical method used to check if current status exist
     *
     * @param statusName - status to check
     */
    public boolean checkStatusExist(String statusName) {
        Status byName = statusRepository.findByName(statusName);
        if (byName == null) {
            throw new NotFoundException(format("Status named %s was not found, you should create it", statusName));
        }
        return true;
    }

}
