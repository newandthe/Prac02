package com.example.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExecuteResult {
    private static int status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;


}
