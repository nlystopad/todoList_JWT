package com.example.todolist_jwt.model.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private Status status;
    private LocalDateTime endDate;
    private LocalDateTime startDate = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Person person;

}