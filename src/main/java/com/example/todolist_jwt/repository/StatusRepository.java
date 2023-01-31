package com.example.todolist_jwt.repository;

import com.example.todolist_jwt.model.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
        Status findByName(String name);
        }
