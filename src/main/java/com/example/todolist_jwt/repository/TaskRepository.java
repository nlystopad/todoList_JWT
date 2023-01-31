package com.example.todolist_jwt.repository;

import com.example.todolist_jwt.model.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task t inner join Person p on p.id =:personId")
    List<Task> findAllByPersonId(Integer personId);

    @Query("select t from Task t inner join Person p on p.id =:personId where t.id =:taskId")
    Task findById(Integer personId, Integer taskId);
}
