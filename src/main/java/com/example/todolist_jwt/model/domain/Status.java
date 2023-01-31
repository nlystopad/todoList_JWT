package com.example.todolist_jwt.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "statuses")
    @JsonIgnore
    private Set<Person> people;
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Task> tasks;

}
