package com.example.todolist_jwt.utils.configuration.json;

import com.example.todolist_jwt.utils.configuration.json.serializer.LocalDateDeserializer;
import com.example.todolist_jwt.utils.configuration.json.serializer.LocalDateSerializer;
import com.example.todolist_jwt.utils.configuration.json.serializer.LocalDateTimeDeserializer;
import com.example.todolist_jwt.utils.configuration.json.serializer.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        return objectMapper;
    }
}
