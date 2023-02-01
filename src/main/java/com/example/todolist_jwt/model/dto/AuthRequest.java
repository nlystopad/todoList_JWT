package com.example.todolist_jwt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AuthRequest {
    @Email @NotNull
    private String email;
    @NotNull
    private String password;
}
